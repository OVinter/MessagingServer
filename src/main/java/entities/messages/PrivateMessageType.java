package entities.messages;

public enum PrivateMessageType {
    PROVIDER_RESPONSE, // return the number of the coffees(more or less the needed stuff to make a coffee)
    MENU_MESSAGE, // the coffee shop sends the menu to the customer
    COFFEE_MESSAGE, // the customer send the message to the coffee shop (the customer wants a coffee)
    RESPONSE_TO_CUSTOMER, // the coffee shop send to customer if the order was completed
    RUN_OUT_OF_COFFEE, // the message that the coffee sends to the provider
}
