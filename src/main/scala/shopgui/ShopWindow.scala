package shopgui

import java.awt.MediaTracker

import javax.swing.{Icon, ImageIcon}

import scala.collection.mutable.{ArrayBuffer, Map}
import scala.collection.mutable.Set
import scala.swing._
import scala.swing.event.SelectionChanged

class ShopWindow(name: String) extends MainFrame{

  /** Constructor if no name is passed */
  def this() = this("SPOS")

  private var itemList = Set[Item]()

  private var userList = List[String]()

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

  import java.awt.Dimension
  import java.awt.Toolkit

  val screenSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize
  val defaultWidth: Double = screenSize.getWidth / 2
  val defaultHeight: Double = screenSize.getHeight / 2
  preferredSize = new Dimension(defaultWidth.toInt, defaultHeight.toInt)

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

  /** Change size of window */
  def resizeWindow(width: Int, height: Int): Unit = {
    this.size_=(new Dimension(width, height))
  }

  /** Add an item to the window */
  def addItem(itemToAdd: Item): Unit = {
    itemList = itemList + itemToAdd
    mainPanel.layout.update(getItemsPanel(), BorderPanel.Position.Center)

    mainPanel.revalidate()
  }

  /** Remove an item from the window (0->not found, 1->removed) */
  def removeItem(itemName: String): Boolean = {
    var success = false
    val item = getItemFromList(itemName)
    if(item != null){
      itemList.remove(item)
      success = true
      mainPanel.layout.update(getItemsPanel(), BorderPanel.Position.Center)
      mainPanel.revalidate()
    }
    success
  }

  def changeReceiptHeader(newHeader: String): Unit = {
    receiptHeader = newHeader
  }

  def changeReceiptFooter(newFooter: String): Unit = {
    receiptFooter = newFooter
  }

  def addUserToList(newUser: String): Unit = {
    userList = userList ::: List[String](newUser)
    cb.peer.setModel(ComboBox.newConstantModel(userList))
  }

  def removeUserFromList(userToRemove: String): Unit = {
    userList = userList.filterNot( userRemove => userRemove == userToRemove)
    cb.peer.setModel(ComboBox.newConstantModel(userList))
  }

  def updateInventory(itemName: String, quantity: Int): Boolean ={
    val item = getItemFromList(itemName)
    if(item != null){
      manageInventory(item, quantity)
      true
    } else false
  }

  def addInventory(itemName: String, quantity: Int): Boolean ={
    val item = getItemFromList(itemName)
    if(item != null){
      manageInventory(item, item.inventory + quantity)
      true
    } else false
  }

  private def manageInventory(item: Item, quantity: Int): Unit ={
    item.inventory = quantity
    revalidateItemsPanel()
  }

  def updatePrice(itemName: String, price: Double): Boolean ={
    val item = getItemFromList(itemName)
    if(item != null){
      item.price = price
      revalidateItemsPanel()
      true
    } else false
  }

  def updateCategory(itemName: String, category: String): Boolean ={
    val item = getItemFromList(itemName)
    if(item != null){
      item.category = category
      revalidateItemsPanel()
      true
    } else false
  }

  def updatePhoto(itemName: String, imgName: String): Boolean ={
    val item = getItemFromList(itemName)
    if(item != null){
      item.photo = imgName
      revalidateItemsPanel()
      true
    } else false
  }

  def addToCartLex(itemName: String, quantity: Int): Boolean ={
    val item = getItemFromList(itemName)
    if(item != null && item.inventory >= quantity){
      addToCart(item, quantity)
      true
    } else false
  }

  def removeFromCartLex(itemName: String, quantity: Int): Boolean ={
    val item = getItemFromList(itemName)
    if(item != null && cart.contains(item) && cart(item) >= quantity){
      removeFromCart(item, quantity)
      true
    } else false
  }

  private def revalidateItemsPanel(): Unit ={
    mainPanel.layout.update(getItemsPanel(), BorderPanel.Position.Center)
    mainPanel.revalidate()
  }

  private def getItemFromList(itemName: String): Item ={
    for(i <- itemList)
      if(i.name == itemName) {
        return i
      }
    null
  }

  /** Here you would pass the totals and the name of the items to pass to the receipt and then clear them for the next transaction */
  private def checkout(): Unit = {
    if(cart.size > 0)
      displayReceipt()
    removeFromTotal(transactionTotal)
    cart.clear()
    updateCart()
  }

  private def displayReceipt(): Unit = {
    var items = new StringBuilder()
    for ((k,v) <- cart) {
      items ++= k.name + " (quantity: " + v.toString + "   price: " + k.price.toString + ")\n"
    }

    var userToDisplay = cb.selection.item

    Dialog.showMessage(contents.head, receiptHeader + "\n\n" + "Served by: " + userToDisplay + "\n\n" + "\n\n" + items + "\n\n" + "Total: " + transactionTotal + "\n\n" + receiptFooter, title="Receipt")
  }

  private def addToCart(item: Item, quantity: Integer = 1): Unit = {
    // update item inventory
    addToTotal(item.price * quantity)
    item.removeInventory(quantity)

    //add item to cart collection (includes quantity as map value)
    if(cart.contains(item)) cart += (item -> (cart(item)+quantity)) else (cart += (item -> quantity))

    //update displayed cart
    updateCart()

    // update item data, including quantity
    revalidateItemsPanel()
  }

  private def updateCart(): Unit ={
    val p = new ScrollPane(new BoxPanel(Orientation.Vertical) {
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

  private def removeFromCart(item: Item, quantity: Integer = 1): Unit = {
    // update item inventory
    removeFromTotal(item.price * quantity)
    item.addInventory(quantity)

    //remove item to cart collection (includes quantity as map value)
    if(cart.contains(item)) {
      if (cart(item) == quantity)
        cart -= (item)
      else
        cart += (item -> (cart(item) - quantity))
    }

    //update displayed cart
    updateCart()

    //update the items panel
    revalidateItemsPanel()
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
    boxPanel.repaint()
  }

  private def getItemsPanel(): FlowPanel ={
    new FlowPanel() {
      for (i <- itemList) {
        contents += new BoxPanel(Orientation.Vertical) {
          var icon = new ImageIcon("src/media/products/" + i.photo)
          if(icon.getImageLoadStatus != MediaTracker.COMPLETE) icon = new ImageIcon("src/media/products/no-image.png")
          var addToCartButton = Button("") { addToCart(i) }
          addToCartButton.enabled_=(i.inventory > 0)
          addToCartButton.icon_=(icon)
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
