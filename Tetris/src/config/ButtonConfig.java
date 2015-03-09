package config;

import org.dom4j.Element;

public class ButtonConfig {
	
	private final int buttonW;
	
	private final int buttonH;
	
	private final int startX;
	
	private final int startY;
	
	private final int userconfigX;
	
	private final int userconfigY;
	
	public ButtonConfig(Element button){
		this.buttonW = Integer.parseInt(button.attributeValue("w"));
		this.buttonH = Integer.parseInt(button.attributeValue("h"));
		this.startX = Integer.parseInt( button.element("start").attributeValue("x") );
		this.startY = Integer.parseInt( button.element("start").attributeValue("y") );
		this.userconfigX = Integer.parseInt( button.element("userConfig").attributeValue("x") );
		this.userconfigY = Integer.parseInt( button.element("userConfig").attributeValue("y") );
	}

	public int getButtonW() {
		return buttonW;
	}

	public int getButtonH() {
		return buttonH;
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public int getUserconfigX() {
		return userconfigX;
	}

	public int getUserconfigY() {
		return userconfigY;
	}
}
