import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.system.Vector2f;

public class Main
{
	public static int angle=0;
	
	public static void main(String args[])
	{
		int width = 800, height = 600;
		
		RenderWindow window = new RenderWindow();
		window.create(new VideoMode(800, 600), "Asteroids");
		
		RectangleShape bg = new RectangleShape(new Vector2f(width, height));
		bg.setPosition(0, 0);
		bg.setFillColor(new Color(125, 0, 0));
		
		RectangleShape p1 = new RectangleShape(new Vector2f(25, 25));
		p1.setPosition((width/2)-(p1.getSize().x/2), (height/2)-(p1.getSize().y/2));
		p1.setOrigin(p1.getSize().x/2, p1.getSize().y/2);
		
		CircleShape bullet = new CircleShape(5);
		bullet.setPosition((float)(p1.getPosition().x+p1.getOrigin().x+Math.cos((double)Math.toDegrees(angle))), (float)(p1.getPosition().y+p1.getOrigin().y+Math.sin((double)Math.toDegrees(angle))));
		bullet.setFillColor(Color.BLACK);
		
		while(window.isOpen())
		{
			window.clear();
			for(Event event : window.pollEvents())
			{
				switch(event.type)
				{
				case CLOSED:
					window.close();
					break;
				case KEY_PRESSED:
					input(p1, bullet, width, height);
					break;
				default:
					break;
				}
			}
			window.draw(bg);
			window.draw(p1);
			window.draw(bullet);
			
			window.display();
		}
	}
	
	public static void input(RectangleShape p1, CircleShape bullet, int width, int height)
	{
		if(Keyboard.isKeyPressed(Keyboard.Key.LEFT))
		{
			p1.rotate(-5);
			if(angle<=-360)
				angle=0;
			else
				angle-=5;
				
		}
		else if(Keyboard.isKeyPressed(Keyboard.Key.RIGHT))
		{
			p1.rotate(5);
			if(angle>=360)
				angle=0;
			else
				angle+=5;
		}
		
		if(Mouse.isButtonPressed(Mouse.Button.LEFT))
		{
			if(angle >= 0 && angle <= 90)
				bullet.setPosition((float)(p1.getPosition().x+p1.getOrigin().x+bullet.getRadius()*2*Math.cos((double)Math.toDegrees(angle))), (float)(p1.getPosition().y+p1.getOrigin().y+bullet.getRadius()*2*Math.sin((double)Math.toDegrees(angle))));
		}
	}
}
