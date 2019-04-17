package shopgui

class Item(itemCategory: String, itemName: String, itemPhoto: String, itemInventory: Int, itemPrice: Double) {
  /** As the class is defined, if an item is initialized with negative values for inventory or price,
    * the value 0 is given. However, if they use negative values when calling the methods, nothing happens. */
  private var _category = itemCategory
  private var _name = itemName
  private var _photo = itemPhoto
  private var _inventory = if (itemInventory > 0) itemInventory else 0
  private var _price = if (itemPrice > 0) itemPrice else 0

  def name: String = _name

  def name_=(newValue: String): Unit = {
    _name = newValue
  }

  def category: String = _category

  def category_=(newValue: String): Unit = {
    _category = newValue
  }

  /** idk what to use for photos so im putting them as strings for now */
  def photo: String = _photo

  def photo_=(newValue: String): Unit = {
    _photo = newValue
  }

  def inventory: Int = _inventory

  def inventory_=(newValue: Int): Unit = {
    _inventory = if (newValue >= 0) newValue else _inventory
  }

  def price: Double = _price

  def price_=(newValue: Double): Unit = {
    _price = if (newValue >= 0) newValue else _price
  }


  def addInventory(amountToAdd: Int): Unit = {
    _inventory = if (amountToAdd > 0) _inventory + amountToAdd else _inventory
  }

  def removeInventory(amountToRemove: Int): Unit = {
    _inventory = if (amountToRemove > 0) _inventory - amountToRemove else _inventory
  }


}
