package shopgui

import scala.swing._

class ShopWindow(name: String) extends MainFrame{

  /* Constructor if no name is passed */
  def this() = this("SPCS")

//  private var itemList = List[Item]()
  // Just testing :)
  private var itemList = List(new Item("idk", "coca-cola", "123", 10, 0.99),
    new Item("idk", "arroz", "123", 22, 2.99))

  private var transactionTotal: Double = 0

  private var receiptHeader = "This is the default receipt header. \n" +
    "Please type in the command 'receiptHeader: [your new header goes here]' in the console to change it"
  private var receiptFooter = "Thank you for shopping with us! \n" +
    "This is the default receiptFooter. To change it type the command 'receiptFooter: [new footer]' in the console."

  /* MainFrame class actions */
  title = name
  preferredSize = new Dimension(800, 500)

  private var totalLabelBox = new BoxPanel(Orientation.Horizontal){
    contents += new Label("Total: $" + transactionTotal)
  }

  contents = new BoxPanel(Orientation.Vertical) {

    contents += Button("Checkout") { checkout() }
    contents += new BoxPanel(Orientation.Horizontal) {
      //Testing how to add items
      for (i <- itemList) {

        contents += new BoxPanel(Orientation.Vertical) {
          contents += Button(i.name) { addToTotal(i.price) }
          contents += Swing.VStrut(10)
          contents += new Label("$" + i.price)
          contents += Swing.VStrut(10)
          contents += new Label("Amount left: " + i.inventory)
          contents += Button("Remove from cart") { removeFromTotal(i.price) }

        }
      }
    } // End items section
    contents += totalLabelBox

  } // End contents


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

  private def addToTotal(amountToAdd: Double): Unit = {
    transactionTotal += amountToAdd
    totalLabelBox.repaint()
    println("transactionTotal: " + transactionTotal)
  }

  /* This doesn't have a condition for if the total gets to be less than 0 because the button should only be clickable with items that
   * are already part of the total so the amount should never be negative */
  private def removeFromTotal(amountToRemove: Double): Unit = {
    transactionTotal = transactionTotal - amountToRemove
    totalLabelBox.repaint()
    println("transactionTotal: " + transactionTotal)
  }


}
