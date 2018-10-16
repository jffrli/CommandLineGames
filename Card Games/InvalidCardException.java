/**
 * InvalidCardException indicates a card is invalid
*/
public class InvalidCardException extends RuntimeException	{
	
	/**
	 * Creates an exception related to an invalid card trying to be created.
	 * @param msg Some information about the cause of the exception.
	 * The message can be one of the following:
	 *	<ul>
	 * <li>Number out of range</li>
	 * <li>Suit does not exist</li>
	 * </ul>
	 */
	public InvalidCardException(String msg) {
		super(msg);
	}

	/**
	 * Creates an exception related to an invalid card.
	 */
	public InvalidCardException() {
		super();
	}

}