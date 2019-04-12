package shopgui

import scala.swing._

class ShopWindow(name: String) extends MainFrame{

  /** To Do:
    * -GridBag Panel can be used to divide into cart and items sections http://otfried.org/scala/index_42.html
    * -Section with all items on cart and their amount and total
    * -Only can hit remove from cart button if its on cart (this button could also be next to the item on the cart section)
    * -[DONE] Lower inventory with every click and restore it if item removed from cart
    * -[DONE] Only can add to cart if enough inventory
    * -Pass info to the receipt
    * -Label that says the amount there is of an item in the cart currently (thinking of making it a var in Item class)
    * -add images for items and change on Item class (currently images are set as strings)
    * */

  /** Constructor if no name is passed */
  def this() = this("SPCS")

//  private var itemList = List[Item]()
  // Just testing :)
  private var itemList = List(new Item("idk", "coca-cola", "123", 10, 0.99),
    new Item("idk", "arroz", "123", 54, 2.99),
    new Item("idk", "pizza", "123", 49, 14.99),
    new Item("idk", "coco", "123", 11, 2.99),
    new Item("idk", "doritos", "123", 102, 1.37),
    new Item("idk", "guitarra", "123", 22, 100),
    new Item("idk", "avestruz", "123", 1, 100000))

  private var transactionTotal: Double = 0

  private var receiptHeader = "This is the default receipt header. \n" +
    "Please type in the command 'receiptHeader: [your new header goes here]' in the console to change it"
  private var receiptFooter = "Thank you for shopping with us! \n" +
    "This is the default receiptFooter. To change it type the command 'receiptFooter: [new footer]' in the console."

  /* MainFrame class actions */
  title = name
  preferredSize = new Dimension(800, 500)

  private var totalLabel = new Label("Total: $" + transactionTotal)

  contents = new BorderPanel {
    /** Left Panel - Product Display*/
    add(new FlowPanel() {
      for (i <- itemList) {
        contents += new BoxPanel(Orientation.Vertical) {
          var addToCartButton = Button(i.name) { addToCart(i, this) }
          addToCartButton.enabled_=(i.inventory > 0)
          contents += addToCartButton
          contents += Swing.VStrut(10)
          contents += new Label("$" + i.price)
          contents += Swing.VStrut(10)
          contents += new Label("Amount left: " + i.inventory)
          contents += Button("Remove from cart") { removeFromCart(i, this) }
          border = Swing.MatteBorder(1, 1, 1, 1, java.awt.Color.BLACK)
        }
      }
    }, BorderPanel.Position.Center)

    /** Right Panel - Invoice Display*/
    val invoice = new BoxPanel(Orientation.Vertical){
      for (i <- itemList) {
        contents += new BoxPanel(Orientation.Vertical) {
          var addToCartButton = Button("Added Item") { addToCart(i, this) }
          addToCartButton.enabled_=(i.inventory > 0)
          contents += addToCartButton
        }
      }

    }
    invoice.background = new Color(255,255,255) //set background white
    add(invoice, BorderPanel.Position.East)
  }

  /** Change main screen name */
  def renameShop(newName: String): Unit = {
    title = newName
  }

  /** Add an item to the window */
  def addItem(itemToAdd: Item): Unit = {
    itemList = itemList ::: List(itemToAdd)
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

  /** Here you would pass the totals and the name of the items to pass to the receipt and then clear them for the next transaction */
  private def checkout(): Unit = {
    displayReceipt()
    transactionTotal = 0
  }

  private def displayReceipt(): Unit = {
    Dialog.showMessage(contents.head, receiptHeader + "\n\n\n\nContent for receipt goes here\n\n\n\n" + receiptFooter, title="Receipt")
  }

  private def addToCart(item: Item, boxPanel: BoxPanel): Unit = {
    // update item inventory
    addToTotal(item.price)
    item.removeInventory(1)
    println("inventory: " + item.inventory)

    // disable button if necessary
    if (item.inventory <= 0) {
      val newButton = boxPanel.contents.head
      newButton.enabled_=(false)
      boxPanel.contents.update(0, newButton)
    }

    // update amount left label
    updateAmountLabel(item, boxPanel)
//    println(item.inventory)

  }

  private def addToTotal(amountToAdd: Double): Unit = {
    transactionTotal += amountToAdd
    totalLabel.text_=("Total: $" + f"$transactionTotal%1.2f")
    println("transactionTotal: " + transactionTotal)
  }

  private def removeFromCart(item: Item, boxPanel: BoxPanel): Unit = {
    // update item inventory
    removeFromTotal(item.price)
    item.addInventory(1)
    println("inventory: " + item.inventory)

    // enable button if necessary
    if (item.inventory > 0) {
      val newButton = boxPanel.contents.head
      newButton.enabled_=(true)
      boxPanel.contents.update(0, newButton)
    }

    // update amount left label
    updateAmountLabel(item, boxPanel)
//    println(item.inventory)

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
//    val elements = boxPanel.contents.toArray
    //println(elements(4))
    boxPanel.contents.update(4, newAmountLeft)
//    val elements2 = boxPanel.contents.toArray
//    println(elements2(4).toString())
    boxPanel.repaint()
  }


}
