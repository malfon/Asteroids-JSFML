import java.io.IOException;
import java.nio.file.Paths;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.event.Event;
import org.jsfml.system.Vector2f;

public class Main
{
	public static double angle=Math.PI;
	public static boolean shot=false;
	public static double currectangle = 0;
	
	public static void main(String args[])
	{
		int width = 800, height = 600;
		
		RenderWindow window = new RenderWindow();
		window.create(new VideoMode(800, 600), "Asteroids");
		
		RectangleShape bg = new RectangleShape(new Vector2f(width, height));
		bg.setPosition(0, 0);
		bg.setFillColor(new Color(0, 0, 0));
		
		Texture p1 = new Texture();
		try
		{
			p1.loadFromFile(Paths.get("bin/p1.png"));
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		Sprite p1s = new Sprite();
		p1s.setTexture(p1);
		p1s.setPosition((width/2)-(p1.getSize().x/2), (height/2)-(p1.getSize().y/2));
		p1s.setOrigin(p1.getSize().x/2, p1.getSize().y/2);
		
		CircleShape bullet = new CircleShape(5);
		bullet.setFillColor(Color.GREEN);
			
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
					input(p1s, bullet, width, height);
					break;
				default:
					break;
				}
			}
			window.draw(bg);
			window.draw(p1s);
			if(shot)
			{
				currectangle=angle;
				bullet.move(-(float)(Math.sin(Math.toRadians(currectangle))), -(float)(Math.cos(Math.toRadians(currectangle))));
			}
			if(shot)
				window.draw(bullet);
			
			window.display();
		}
	}
	
	public static void input(Sprite p1, CircleShape bullet, int width, int height)
	{
		//ccw rotation
		if(Keyboard.isKeyPressed(Keyboard.Key.LEFT))
		{
			p1.rotate((float)(-Math.PI/2));
			if(angle<=-2*Math.PI)
				angle=0;
			else
				angle+=Math.PI/2;
				
		}
		//cc rotation
		else if(Keyboard.isKeyPressed(Keyboard.Key.RIGHT))
		{
			p1.rotate((float)(Math.PI/2));
			if(angle>=2*Math.PI)
				angle=0;
			else
				angle-=Math.PI/2;
		}
		
		//check for shot
		if(Keyboard.isKeyPressed(Key.SPACE))
		{
			shot=true;
			bullet.setPosition(p1.getPosition().x+p1.getOrigin().x, p1.getPosition().y+p1.getOrigin().y);
		}
		
		//bullet exiting
		if(bullet.getPosition().x<-bullet.getRadius()*2 || bullet.getPosition().x>width || bullet.getPosition().y<-bullet.getRadius()*2 || bullet.getPosition().y>height)
			shot=false;
	}
}
