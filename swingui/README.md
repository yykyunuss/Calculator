#Four Operation Calculator

###  Description   
#####This application is a four operation calculator developed with service structure in Eclipse Equinox OSGI framework



###Technologies

1. Java
2. Eclipse RCP
3. OSGI
4. Swing


###Usage
     1. Gets input(two numbers) in font from the user with Swing UI screen.
     2. User clicks one of the add, subtract, mutiply or divide buttons
     3. User see the result of operation
     
###Code Logic

1. There are two OSGI services in application. 
2. numberconversionservice converts words to numbers and numbers to words
3. swingui service creates and opens Java Swing screen.
4. When the application is started, the SwingUIService object is created in the start method of the activator class of the swingui service. Screen components are created by calling the initiliazeUI method. Also, a reference to the other service is taken and its object is set.
5. In SwingUIService class, there are 4 inner ActionListener classes for operation actions. Action listeners are created in the initialize method. Actions are called for four actions with the help of button click.
6. In action listener, number-to-word and word-to-number conversion methods are called. Thus, the other service is used.
7. Two numbers that comes as a text input to the numberconversionservice is converted to a number in the convertToNumber method.
8. Then, result is calculated according to operation.
9. Then these numbers are sent to the convertToWord methods (The appropriate method is called according to the language).
10. Here, it is converted to a font again and return result to swingui service.
11. The output is shown in the result section on the screen.

###Notes
*BigInteger type are used for mathematical calculations. Because large numbers such as decillion cannot be calculated with int or float number types.

*There are two message.properties file for English and Turkish numbers. If input language is English, we call convertToEnglishWord method for english output. Otherwise, we call the method that returns Turkish output by default


