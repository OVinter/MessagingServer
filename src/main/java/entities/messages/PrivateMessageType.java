package entities.messages;

public enum PrivateMessageType {
    RUN_OUT_OF_COFFEE, // the message that the coffee shop sends to the provider
    PROVIDER_RESPONSE, // return the number of the coffees(more or less the needed stuff to make a coffee)
    ASK_MENU, // the customer asks the coffee shop for the menu
    MENU_MESSAGE, // the coffee shop sends the number of coffees available
    COFFEE_MESSAGE, // the customer send the message to the coffee shop (the customer wants N coffees)
    NOT_ENOUGH_COFFEES, //the coffee shop notifies the customer there are not enough coffees as he'd like to order
    BILL_CUSTOMER, // the coffee shop sends the bill to the customer
    PAY_SHOP, //the customer pays the shop
    GIVE_CHANGE, //if the customer pays too much money, the coffee shop gives them change


}
