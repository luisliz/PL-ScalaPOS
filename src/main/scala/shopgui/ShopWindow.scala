package shopgui

import java.awt.MediaTracker

import javax.swing.{Icon, ImageIcon}

import scala.collection.mutable.{ArrayBuffer, Map}
import scala.swing._
import scala.swing.event.SelectionChanged

class ShopWindow(name: String) extends MainFrame{

  /** To Do:
    * -GridBag Panel can be used to divide into cart and items sections http://otfried.org/scala/index_42.html
    * -Section with all items on cart and their amount and total
    * -Only can hit remove from cart button if its on cart (this button could also be next to the item on the cart section)
    * -[DONE] Lower inventory with every click and restore it if item removed from cart
    * -[DONE] Only can add to cart if enough inventory
    * -[DONE]Pass info to the receipt
    * -[DONE]Label that says the amount there is of an item in the cart currently (thinking of making it a var in Item class)
    * -[DONE]add images for items and change on Item class (currently images are set as strings)
    * -[DONE]Deal with add/remove user and displaying the current user properly
    * -[DONE]Add current user label
    * -Make receiptHeader and receiptFooter properly edit the header and footer (rn it simply deletes them)
    * */

  /** Constructor if no name is passed */
  def this() = this("SPOS")

//    private var itemList = List[Item]()
  // Just testing :)
  private var itemList = List(new Item("idk", "coca-cola", "coca-cola.jpg", 10, 0.99),
    new Item("idk", "arroz", "arroz.png", 54, 2.99),
    new Item("idk", "pizza", "pizza.png", 49, 14.99),
    new Item("idk", "coco", "coco.jpg", 11, 2.99),
    new Item("idk", "doritos", "doritos.jpg", 102, 1.37),
    new Item("idk", "guitarra", "guitarra.png", 22, 100),
    new Item("idk", "avestruz", "avestruz.jpg", 1, 100000))

  //list of users
  //private var userList = new ArrayBuffer[String]()
  private var userList = List[String]()
  //private var userIndex = 0 //the idea is to change +1 this number whenever changeUser is called, and use this to access the respective user - this mechanism could be replaced with something like a dropdown menu

  private var transactionTotal: Double = 0

  private var receiptHeader = "This is the default receipt header. \n" +
    "Please type in the command 'receiptHeader: [your new header goes here]' in the console to change it"
  private var receiptFooter = "Thank you for shopping with us! \n" +
    "This is the default receiptFooter. To change it type the command 'receiptFooter: [new footer]' in the console."

  /* Items in cart */
  private var cart:Map[Item,Int] = Map()

  /* Panel containing the cart, will be updated after entries */
  private val cartPanel = new BoxPanel(Orientation.Vertical) {
      contents += new BoxPanel(Orientation.Horizontal) {}
  }

  /* MainFrame class actions */
  title = name
  preferredSize = new Dimension(800, 500)

  private var totalLabel = new Label("Total: $" + transactionTotal)
  private var currentUserLabel = new Label("Current User: ")
  val cb = new ComboBox(userList)

  private val mainPanel = new BorderPanel {
    /** Left Panel - Product Display*/
    add(getItemsPanel(), BorderPanel.Position.Center)

    /** Right Panel - Invoice Display*/
    var invoice = new BorderPanel {
      add(cartPanel, BorderPanel.Position.Center)

      add(new FlowPanel {
        contents += currentUserLabel
        contents += cb
      }, BorderPanel.Position.North)

      add(new FlowPanel {
        contents += totalLabel
        contents += Button("Checkout") { checkout() }
      }, BorderPanel.Position.South)
    }
    invoice.background = new Color(255,255,255) //set background white
    add(invoice, BorderPanel.Position.East)
  }

  contents = mainPanel

  /** Change main screen name */
  def renameShop(newName: String): Unit = {
    title = newName
  }

  /** Add an item to the window */
  def addItem(itemToAdd: Item): Unit = {
    itemList = itemToAdd :: itemList
    mainPanel.layout.update(getItemsPanel(), BorderPanel.Position.Center)
//    mainPanel.repaint()
  }

  /** Remove an item from the window */
  def removeItem(itemToRemove: Item): Unit = {
    itemList = itemList.filter(_ == itemToRemove)
  }

  def changeReceiptHeader(newHeader: String): Unit = {
    receiptHeader = newHeader
  }

  def changeReceiptFooter(newFooter: String): Unit = {
    receiptFooter = newFooter
  }

  //method to add users
  def addUserToList(newUser: String): Unit = {
    //userList.append(newUser)
    userList = userList ::: List[String](newUser)
    cb.peer.setModel(ComboBox.newConstantModel(userList))
  }

  def removeUserFromList(userToRemove: String): Unit = {
    userList = userList.filterNot( userRemove => userRemove == userToRemove)
    cb.peer.setModel(ComboBox.newConstantModel(userList))
  }


  /** Here you would pass the totals and the name of the items to pass to the receipt and then clear them for the next transaction */
  private def checkout(): Unit = {
    if(cart.size > 0)
      displayReceipt()
    transactionTotal = 0
    cart.clear()
    updateCart()
  }

  private def displayReceipt(): Unit = {
    var items = new StringBuilder()
    for ((k,v) <- cart) {
      items ++= k.name + " (quantity: " + v.toString + "   price: " + k.price.toString + ")\n"
    }

    var userToDisplay = cb.selection.item

    Dialog.showMessage(contents.head, receiptHeader + "\n\n" + "Served by: " + userToDisplay + "\n\n" + "\n\n" + items + "\n\n" + receiptFooter, title="Receipt")
  }

  private def addToCart(item: Item, boxPanel: BoxPanel): Unit = {
    // update item inventory
    addToTotal(item.price)
    item.removeInventory(1)
    println("inventory: " + item.inventory)

    // disable add button if necessary
    if (item.inventory <= 0) {
      val newButton = boxPanel.contents.head
      newButton.enabled_=(false)
      boxPanel.contents.update(0, newButton)
    }

    //add item to cart collection (includes quantity as map value)
    if(cart.contains(item)) cart += (item -> (cart(item)+1)) else (cart += (item -> 1))

    //update displayed cart
    updateCart()

    // update amount left label
    updateAmountLabel(item, boxPanel)
    //    println(item.inventory)
  }

  private def updateCart(): Unit ={
    val p = new ScrollPane(new BoxPanel(Orientation.Vertical) {
//      println(cart)
      for ((k,v) <- cart) {
        println(s"key: $k, value: $v")
        contents += new BoxPanel(Orientation.Horizontal) {
          contents += Swing.HStrut(10)
          contents += Button("X") { removeFromCart(k) }
          contents += Swing.HStrut(10)
          contents += new Label(k.name + " (" + v + " * " + k.price + ")")
          contents += Swing.HStrut(10)
        }
      }
    })
    (cartPanel.contents).update(0, p)
    cartPanel.repaint()
  }

  private def addToTotal(amountToAdd: Double): Unit = {
    transactionTotal += amountToAdd
    totalLabel.text_=("Total: $" + f"$transactionTotal%1.2f")
    println("transactionTotal: " + transactionTotal)
  }

  private def removeFromCart(item: Item): Unit = {
    // update item inventory
    removeFromTotal(item.price)
    item.addInventory(1)
    println("inventory: " + item.inventory)

    //remove item to cart collection (includes quantity as map value)
    if(cart.contains(item)) {
      if (cart(item) == 1)
        cart -= (item)
      else
        cart += (item -> (cart(item) - 1))
    }

    //update displayed cart
    updateCart()

    mainPanel.layout.update(getItemsPanel(), BorderPanel.Position.Center)
  }

  /* This doesn't have a condition for if the total gets to be less than 0 because the button should only be clickable with items that
   * are already part of the total so the amount should never be negative */
  private def removeFromTotal(amountToRemove: Double): Unit = {
    transactionTotal -= amountToRemove
    totalLabel.text_=("Total: $" + f"$transactionTotal%1.2f")
    println("transactionTotal: " + transactionTotal)
  }

  private def updateAmountLabel(item: Item, boxPanel: BoxPanel): Unit = {
    val newAmountLeft = new Label("Amount left: " + item.inventory)
    boxPanel.contents.update(6, newAmountLeft)
//    println(boxPanel.contents)
    boxPanel.repaint()
  }

  private def getItemsPanel(): FlowPanel ={
    new FlowPanel() {
      for (i <- itemList) {
        contents += new BoxPanel(Orientation.Vertical) {
          var icon = new ImageIcon("src/media/products/" + i.photo)
          if(icon.getImageLoadStatus != MediaTracker.COMPLETE) icon = new ImageIcon("src/media/products/no-image.png")
          var addToCartButton = Button("") { addToCart(i, this) }
          addToCartButton.enabled_=(i.inventory > 0)
          addToCartButton.icon_=(icon)
          //          addToCartButton.maximumSize_=(new Dimension(100, 100))
          contents += addToCartButton
          contents += Swing.VStrut(5)
          contents += new Label(i.name)
          contents += Swing.VStrut(5)
          contents += new Label("$" + i.price)
          contents += Swing.VStrut(5)
          contents += new Label("Amount left: " + i.inventory)
          border = Swing.MatteBorder(1, 1, 1, 1, java.awt.Color.BLACK)
        }
      }
    }
  }
}
