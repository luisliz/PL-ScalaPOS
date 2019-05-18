# ScalaPOS
A POS system language programmed in Scala 

**To view the project page click [here](https://luisliz.github.io/ScalaPOS/index.html)**

**To view the full report click [here](https://docs.google.com/document/d/1jCX3KofrjrvbjNLpKXNKypLlAlaS6WcpxJMACgLSczA/edit?usp=sharing)**

## Introduction

  One of the potential project ideas that were presented by Prof. Wilson Rivera for this course was to implement a previous project from the Fall 2018 course using Racket or Scala. Since most of these past projects use Python, we decided to try out Scala. We all agree that this was a good choice since one of our main goals while doing this was to expand our knowledge of new programming languages, and a good way of doing so was to explore the capabilities of the Scala language, while at the same time providing ourselves with a little challenge.

  Our main goal for this project was to implement the Fall 2018 project titled PCS (Personal Cashier System) similarly on Scala, while adding a few more features, such as keeping track of inventory. The idea to re-implement this project with Scala came when we were discussing possible project ideas and we came up with the same project idea as what was proposed by PCS. Not wanting to let the idea go after finding out it had already been done, we opted to reimplement it using Scala, therefore creating our programing language: Scala Point Of Sale (SPOS).

  SPOS is language that enables the creation of a point of sale system with a few simple commands. This system provides a user interface for managing sales, where the user can add items to the point of sale, add new cashiers or “users”, customize the receipt, and then perform the sales, all in one place. The language enables you to write commands to add new items and update their inventories. SPOS takes care of setting up the GUI, adding the items to the grid layout, and handling click inputs for the different functionalities, such as adding items to the cart, checking out the cart, and managing different user accounts.


## Language Tutorial

<a href="http://www.youtube.com/watch?feature=player_embedded&v=aiCs62_dnCM
" target="_blank"><img src="http://img.youtube.com/vi/aiCs62_dnCM/0.jpg" 
alt="IMAGE ALT TEXT HERE" width="240" height="180" border="10" /></a>

### The Basics
  Once you have downloaded the language’s source code from our public repository, you can open it in your favorite IDE that supports Scala (we recommend IntelliJ) and run the program. The program will read whatever code is in the file shortFile.spos, found in the project’s main directory, as the input. After the program is running, however, you can still perform SPOS commands through the console, which will take effect immediately on the current instance of the program, though they will not be saved to the file; meaning any changes you make will be reverted if you close the program and run it again.

  You will see that with SPOS you can also add images to each of the items. To do this, you will have to make sure to place all of the images you wish to use in the products folder under the ScalaPOS/src/media/products path. A good convention is to name each picture the same as you plan on naming the item. You will also have to specify the file type (jpg, png, etc.).

### Code Example
```
/* Creates empty shop GUI with the given title */
createShop: “My Shop”

/* Adds specified item to the shop GUI */
addItem: “food”, “arroz”, “arroz.jpg”, 300, 1.50
addItem: “drink”, “cocacola”, “cocacola.jpg”, 500, 1

/* Adds or subtracts inventory to item */
addInventory: “arroz”, 3
removeInventory: “cocacola”, 20

/* Overrides inventory with new parameters */
updateInventory: “arroz”, 200

/* Overrides price */
updatePrice: “arroz”, 1.60

/* Remove an item from the shop GUI */
deleteItem: “cocacola”

/* Set the string passed as an input as the receipt footer */
recepitFooter: “Thank you for shopping with us”
```


**For the SPOS reference manual with a full list of commands, along with info about the language development, translator architecture, testing methodology, etc. please view our full report [here](https://docs.google.com/document/d/1jCX3KofrjrvbjNLpKXNKypLlAlaS6WcpxJMACgLSczA/edit?usp=sharing)**
