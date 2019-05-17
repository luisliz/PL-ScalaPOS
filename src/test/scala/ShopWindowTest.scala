import org.scalatest.FunSuite
import shopgui.{Item, ShopWindow}

class ShopWindowTest extends FunSuite {
  test("Basic constructor") {
    var gui = new ShopWindow
    gui.visible = true
    assert(gui.title == "SPOS")
  }

  test("Named constructor") {
    var gui = new ShopWindow("TestName")
    gui.visible = true
    assert(gui.title == "TestName")
  }

  test("Rename Shop") {
    var gui = new ShopWindow
    gui.visible = true
    gui.renameShop("NewName")
    assert(gui.title == "NewName")
  }

  test("Add inventory") {
    var gui = new ShopWindow
    gui.visible = true
    var item1 = new Item("food", "coke", "photo.jpg", 11, 11.5)
    gui.addItem(item1)
    gui.addInventory("coke", 1)
    assert(item1.inventory == 12)
  }

  test("Remove inventory") {
    var gui = new ShopWindow
    gui.visible = true
    var item1 = new Item("food", "coke", "photo.jpg", 11, 11.5)
    gui.addItem(item1)
    gui.addInventory("coke", -1)
    assert(item1.inventory == 10)
  }

  test("Update inventory") {
    var gui = new ShopWindow
    gui.visible = true
    var item1 = new Item("food", "coke", "photo.jpg", 11, 11.5)
    gui.addItem(item1)
    gui.updateInventory("coke", 15)
    assert(item1.inventory == 15)
  }

  test("Resize Window") {
    var gui = new ShopWindow
    gui.visible = true
    gui.resizeWindow(137, 144)
    assert(gui.size.width == 137 && gui.size.height == 144)
  }

  test("Update photo") {
    var gui = new ShopWindow
    gui.visible = true
    var item1 = new Item("food", "coke", "photo.jpg", 11, 11.5)
    gui.addItem(item1)
    gui.updatePhoto("coke", "newPhoto.png")
    assert(item1.photo == "newPhoto.png")
  }

  test("Update category") {
    var gui = new ShopWindow
    gui.visible = true
    var item1 = new Item("food", "coke", "photo.jpg", 11, 11.5)
    gui.addItem(item1)
    gui.updateCategory("coke", "notFood")
    assert(item1.category == "notFood")
  }

  test("Update price") {
    var gui = new ShopWindow
    gui.visible = true
    var item1 = new Item("food", "coke", "photo.jpg", 11, 11.5)
    gui.addItem(item1)
    gui.updatePrice("coke", 1.37)
    assert(item1.price == 1.37)
  }

}