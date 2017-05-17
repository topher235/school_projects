

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Dictionary;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.junit.Before;
import org.junit.Test;

import edu.cnu.cs.gooey.Gooey;
import edu.cnu.cs.gooey.GooeyFrame;

public class MouseSquaresTest {
	// gooey versioning
	private static final double BUILD_VERSION = 1.8;
	
	@Before
	public void hasUptodateGooeyVersion() {
		String updateGooey = "download a newer version of gooey.jar from <https://github.com/robertoaflores/Gooey>";
		try {
			Class<?> gooey    = Class.forName("edu.cnu.cs.gooey.Gooey");
			Method   version  = gooey.getMethod( "getVersion" );
			Double   actual   = (Double) version.invoke( null );
			double   expected = BUILD_VERSION;
			assertTrue( updateGooey, expected <= actual );
		} catch (ClassNotFoundException | NoSuchMethodException  | SecurityException |
				 IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			fail( updateGooey );
		}
	}
	// structural
	@Test
	public void testStructural_ClassHasPrivateFieldsOnly() {
		Class<?> iClass  = MouseSquares.class;
		Field[]  iFields = iClass.getDeclaredFields();
		for (Field f : iFields) {
			if (!f.isSynthetic()) {
				assertTrue ("Field '" + f.getName() + "' should be private",       Modifier.isPrivate(f.getModifiers()));
				assertFalse("Field '" + f.getName() + "' can't be static",         Modifier.isStatic (f.getModifiers()));
				assertFalse("Field '" + f.getName() + "' can't be of type JFrame", f.getType().isAssignableFrom( JFrame.class ));
			}
		}
		checkClassVariables( MouseSquares.class );
		checkClassVariables( MouseSquares.MouseSquaresPanel.class );
	}
	private void checkClassVariables(Class<?> iClass) {
		Field[]  iFields = iClass.getDeclaredFields();
		for (Field f : iFields) {
			if (!f.isSynthetic()) {
				assertFalse("Field '" + f.getName() + "' can't be a Java Collection", Collection .class.isAssignableFrom( f.getType() ));
				assertFalse("Field '" + f.getName() + "' can't be a Java Collection", Dictionary .class.isAssignableFrom( f.getType() ));
				assertFalse("Field '" + f.getName() + "' can't be a Java Collection", AbstractMap.class.isAssignableFrom( f.getType() ));
				assertFalse("Field '" + f.getName() + "' can't be a Java array",      f.getType().isArray());
			}
		}
	}
	// non-functional
	@Test
	public void testNonFunctional_WindowHasTypeTitleLocationSizeMenus() throws NoSuchMethodException, SecurityException {
		Gooey.capture(new GooeyFrame() {
			@Override
			public void invoke() {
				MouseSquares.main( new String[]{} );
			}
			@Override
			public void test(JFrame f) {
				{
					Class<?> isa    = MouseSquares.class.getSuperclass();
					assertTrue( "Class should extend JFrame", isa == JFrame.class );
				}
				{
					Class<?> expected = MouseSquares.class;
					Class<?> actual   = f.getClass();
					assertEquals( "Window isn't of expected type", expected, actual );
				}{
					String expected = "MouseSquares";
					String actual   = f.getTitle();
					assertEquals( "Incorrect window title", expected, actual );
				}{
					Dimension actual   = f.getSize();
					Dimension expected = new Dimension( 500, 500 );
					assertEquals( "Window has incorrect dimensions", expected, actual );
				}{
					Point actual   = f.getLocation();
					f.setLocationRelativeTo( null );
					Point expected = f.getLocation();
					assertEquals( "Window not centered on screen", expected, actual );
				}{
					JMenuBar     menubar  = Gooey.getMenuBar( f );
					List<JMenu>  actual   = Gooey.getMenus( menubar );
					List<String> expected = new ArrayList<>(Arrays.asList( new String[]{ "Program", "Edit" } )); 
					for (JMenu menu : actual) {
						String label = menu.getText();
						if (expected.contains(label)) {
							expected.remove  (label);
						} else {
							fail( "Unexpected menu found: " + label );
						}
					}
					if (!expected.isEmpty()) {
						fail( "Expected menus not found: " + expected );
					}
				}{
					JMenuBar        menubar = Gooey.getMenuBar( f );
					JMenu           program = Gooey.getMenu( menubar, "Program" );
					List<JMenuItem> actual  = Gooey.getMenus( program );
					assertEquals( "'Program' menu should have one menu item", 1, actual.size() );
					assertEquals( "'Program' menu has unexpected menu item", "Exit", actual.get(0).getText() );
					assertTrue  ( "'Exit' menu item should be enabled",              actual.get(0).isEnabled() );
				}{
					JMenuBar        menubar  = Gooey.getMenuBar( f );
					JMenu           edit     = Gooey.getMenu( menubar, "Edit" );
					List<JMenuItem> actual   = Gooey.getMenus( edit );
					assertEquals( "'Edit' menu should have two menu items", 2, actual.size() );
					List<String>    expected = new ArrayList<>(Arrays.asList( new String[]{ "Undo", "Redo" } )); 
					for (JMenuItem menu : actual) {
						String label = menu.getText();
						if (expected.contains(label)) {
							assertFalse( "'"+label+"' menu item should be disabled", menu.isEnabled() );
							expected.remove  (label);
						} else {
							fail( "Unexpected menu item found: " + label );
						}
					}
					if (!expected.isEmpty()) {
						fail( "Expected menu items not found: " + expected );
					}
				}
			}
		});
	}
	// functional
	@Test
	public void testFunctional_WindowClosesFromMenuAndCloseIcon() {
		Gooey.capture(new GooeyFrame() {
			@Override
			public void invoke() {
				MouseSquares.main( new String[]{} );
			}
			@Override
			public void test(JFrame f) {
				int actual   = f.getDefaultCloseOperation();
				int expected = JFrame.DISPOSE_ON_CLOSE;
				assertTrue( "Window doesn't implement DISPOSE_ON_CLOSE", expected == actual );

				f.dispatchEvent(new WindowEvent( f, WindowEvent.WINDOW_CLOSING ));
				assertFalse( "Window should close when clicking on its close icon", f.isShowing() );
			}
		});
		Gooey.capture(new GooeyFrame() {
			@Override
			public void invoke() {
				MouseSquares.main( new String[]{} );
			}
			@Override
			public void test(JFrame f) {
				int actual   = f.getDefaultCloseOperation();
				int expected = JFrame.DISPOSE_ON_CLOSE;
				assertTrue( "Window doesn't implement DISPOSE_ON_CLOSE", expected == actual );

				JMenuBar  menubar = Gooey.getMenuBar(f);
				JMenu     song    = Gooey.getMenu( menubar, "Program" );
				JMenuItem exit    = Gooey.getMenu( song,    "Exit" );
				exit.doClick();
				assertFalse( "Window should close when using the 'Exit' menu", f.isShowing() );
			}
		});
	}
	private static void clickMouse(JFrame frame, JPanel panel, int x, int y) {
		try {
			SwingUtilities.invokeAndWait( ()-> {
			panel.dispatchEvent( new MouseEvent( 
			                     frame,                      // event source 
			                     MouseEvent.MOUSE_CLICKED,   // event id 
			                     System.currentTimeMillis(), // time when it happened
			                     MouseEvent.BUTTON1,         // event modifiers
			                     x, y,                       // (x,y) location
			                     1,                          // click count
			                     false ));                   // triggers popup?
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testFunctional_AddOneSquareCanUndoAndRedo() {
		// 1. (mouse: add one square)[undo !redo]
		// 2. (get squares)[copy]
		// 3. (menu: undo)[!undo redo]
		// 4. (get squares)[empty]
		// 5. (menu: redo)[undo !redo]
		// 6. (get squares)[equal copy]
		Gooey.capture(new GooeyFrame() {
			@Override
			public void invoke() {
				MouseSquares.main( new String[]{} );
			}
			@Override
			public void test(JFrame f) {
				JMenuBar     menubar = Gooey.getMenuBar( f );
				JMenu        edit    = Gooey.getMenu( menubar, "Edit" );
				JMenuItem    undo    = Gooey.getMenu( edit, "Undo" );
				JMenuItem    redo    = Gooey.getMenu( edit, "Redo" );

				JPanel       panel   = Gooey.getComponent( f, MouseSquares.MouseSquaresPanel.class );
				MouseSquares frame   = null;
				if (f instanceof MouseSquares) frame = (MouseSquares) f;
				else                           fail( "Window is not an instance of MouseSquares" );

				// 1. (mouse: add one square)[undo !redo]
				clickMouse  ( frame, panel, 50, 80 );
				assertTrue  ( "'Undo' menu should be enabled",  undo.isEnabled() );
				assertFalse ( "'Redo' menu should be disabled", redo.isEnabled() );
				assertEquals( "Undo stack should have 1 command", 1, frame.getUndo().getSize() );
				assertEquals( "Redo stack should be empty",       0, frame.getRedo().getSize() );
				// 2. (get squares)[copy]
				Stack<ColoredSquare> squares = frame.getSquares();
				assertEquals( "Stack of squares should have one square", 1, squares.getSize() );
				List <ColoredSquare> copy    = new ArrayList<>();
				for (ColoredSquare s : squares) {
					copy.add( s.clone() );
				}
				assertEquals( "Unexpected square data", new Point( 50, 80 ), copy.get(0).getLocation() );
				// 3. (menu: undo)[!undo redo]
				undo.doClick();
				assertFalse( "'Undo' menu should be disabled", undo.isEnabled() );
				assertTrue ( "'Redo' menu should be enabled",  redo.isEnabled() );
				assertEquals( "Undo stack should be empty",       0, frame.getUndo().getSize() );
				assertEquals( "Redo stack should have 1 command", 1, frame.getRedo().getSize() );
				// 4. (get squares)[empty]
				squares = frame.getSquares();
				assertEquals( "Stack of squares should be empty", 0, squares.getSize() );
				// 5. (menu: redo)[undo !redo]
				redo.doClick();
				assertTrue ( "'Undo' menu should be enabled",  undo.isEnabled() );
				assertFalse( "'Redo' menu should be disabled", redo.isEnabled() );
				assertEquals( "Undo stack should have 1 command", 1, frame.getUndo().getSize() );
				assertEquals( "Redo stack should be empty",       0, frame.getRedo().getSize() );
				// 6. (get squares)[equal copy]
				squares = frame.getSquares();
				for (ColoredSquare s : squares) {
					assertEquals( "Unexpected square", copy.remove(0), s );
				}
			}
		});
	}
	@Test
	public void testFunctional_AddThreeSquaresUndoTwoAddOne() {
		// 1. (mouse: add three squares)[undo !redo]
		// 2. (get squares)[copy]
		// 3. (menu: undo twice)[undo redo]
		// 4. (get squares)[has one]
		// 5. (mouse: add one new square)[undo !redo]
		// 6. (get squares)[equal one+new]
		Gooey.capture(new GooeyFrame() {
			@Override
			public void invoke() {
				MouseSquares.main( new String[]{} );
			}
			@Override
			public void test(JFrame f) {
				JMenuBar     menubar = Gooey.getMenuBar( f );
				JMenu        edit    = Gooey.getMenu( menubar, "Edit" );
				JMenuItem    undo    = Gooey.getMenu( edit, "Undo" );
				JMenuItem    redo    = Gooey.getMenu( edit, "Redo" );

				JPanel       panel   = Gooey.getComponent( f, MouseSquares.MouseSquaresPanel.class );
				MouseSquares frame   = null;
				if (f instanceof MouseSquares) frame = (MouseSquares) f;
				else                           fail( "Window is not an instance of MouseSquares" );

				Point[] mouse = { new Point(30,130), new Point(142,42), new Point(9,11) };
				// 1. (mouse: add three squares)[undo !redo]
				for (int i = 0; i < mouse.length; i++) {
					clickMouse  ( frame, panel, mouse[i].x, mouse[i].y );
					assertTrue  ( "'Undo' menu should be enabled",  undo.isEnabled() );
					assertFalse ( "'Redo' menu should be disabled", redo.isEnabled() );
					assertEquals( "Undo stack should have "+(i+1)+" command(s)", i+1, frame.getUndo().getSize() );
					assertEquals( "Redo stack should be empty",                  0,   frame.getRedo().getSize() );
				}
				// 2. (get squares)[copy]
				Stack<ColoredSquare> squares = frame.getSquares();
				assertEquals( "Stack of squares should have several squares", mouse.length, squares.getSize() );
				List <ColoredSquare> copy    = new ArrayList<>();
				for (ColoredSquare s : squares) {
					copy.add( s.clone() );
				}
				for (int i=0; i<mouse.length; i++) {
					Point actual   = copy.get(i).getLocation();
					Point expected = mouse[i];
					assertEquals( "Unexpected data on square "+i, expected, actual );
				}
				// 3. (menu: undo twice)[undo redo]
				undo.doClick();
				assertTrue ( "'Undo' menu should be enabled", undo.isEnabled() );
				assertTrue ( "'Redo' menu should be enabled", redo.isEnabled() );
				assertEquals( "Undo stack should have two commands", 2, frame.getUndo().getSize() );
				assertEquals( "Redo stack should have one command",  1, frame.getRedo().getSize() );

				undo.doClick();
				assertTrue ( "'Undo' menu should be enabled", undo.isEnabled() );
				assertTrue ( "'Redo' menu should be enabled", redo.isEnabled() );
				assertEquals( "Undo stack should have one commands", 1, frame.getUndo().getSize() );
				assertEquals( "Redo stack should have two command",  2, frame.getRedo().getSize() );
				// 4. (get squares)[has one]
				squares = frame.getSquares();
				assertEquals( "Stack of squares should have one square", 1, squares.getSize() );
				for (ColoredSquare s : squares) {
					assertEquals( "Unexpected square", copy.get(0), s );
				}
				// 5. (mouse: add one new square)[undo !redo]
				Point[] andNow = { mouse[0], new Point( 250, 186 ) };
				clickMouse  ( frame, panel, andNow[1].x, andNow[1].y );
				assertTrue  ( "'Undo' menu should be enabled",  undo.isEnabled() );
				assertFalse ( "'Redo' menu should be disabled", redo.isEnabled() );
				assertEquals( "Undo stack should have two commands", 2, frame.getUndo().getSize() );
				assertEquals( "Redo stack should be empty",          0, frame.getRedo().getSize() );
				// 6. (get squares)[equal one+new]
				squares = frame.getSquares();
				assertEquals( "Stack of squares should have two squares", 2, squares.getSize() );
				List<Point> expected = new ArrayList<>( Arrays.asList( andNow )); 
				for (ColoredSquare s : squares) {
					assertEquals( "Unexpected square", expected.remove(0), s.getLocation() );
				}
			}
		});
	}
	@Test
	public void testFunctional_AddSeveralSquaresUndoAllRedoAll() {
		// 1. (mouse: add many squares)[undo !redo]
		// 2. (get squares)[copy]
		// 3. (menu: undo all)[!undo redo]
		// 4. (get squares)[is empty]
		// 5. (menu: redo all)[undo !redo]
		// 6. (get squares)[equal copy]
		Gooey.capture(new GooeyFrame() {
			@Override
			public void invoke() {
				MouseSquares.main( new String[]{} );
			}
			@Override
			public void test(JFrame f) {
				JMenuBar     menubar = Gooey.getMenuBar( f );
				JMenu        edit    = Gooey.getMenu( menubar, "Edit" );
				JMenuItem    undo    = Gooey.getMenu( edit, "Undo" );
				JMenuItem    redo    = Gooey.getMenu( edit, "Redo" );

				JPanel       panel   = Gooey.getComponent( f, MouseSquares.MouseSquaresPanel.class );
				MouseSquares frame   = null;
				if (f instanceof MouseSquares) frame = (MouseSquares) f;
				else                           fail( "Window is not an instance of MouseSquares" );

				Point[] mouse = { 
						new Point( 0, 0), new Point(100,110), new Point(200,220), new Point(300,230),
						new Point(50,20), new Point(150,140), new Point(250,260), new Point(350,280),
						new Point(80, 0), new Point(80, 150), new Point(280,120), new Point(390,350)
				};
				// 1. (mouse: add several squares)[undo !redo]
				for (int i = 0; i < mouse.length; i++) {
					clickMouse  ( frame, panel, mouse[i].x, mouse[i].y );
					assertTrue  ( "'Undo' menu should be enabled",  undo.isEnabled() );
					assertFalse ( "'Redo' menu should be disabled", redo.isEnabled() );
					assertEquals( "Undo stack should have "+(i+1)+" command(s)", i+1, frame.getUndo().getSize() );
					assertEquals( "Redo stack should be empty",                  0,   frame.getRedo().getSize() );
				}
				// 2. (get squares)[copy]
				Stack<ColoredSquare> squares = frame.getSquares();
				assertEquals( "Stack of squares should have several squares", mouse.length, squares.getSize() );
				List <ColoredSquare> copy    = new ArrayList<>();
				for (ColoredSquare s : squares) {
					copy.add( s.clone() );
				}
				for (int i=0; i<mouse.length; i++) {
					Point actual   = copy.get(i).getLocation();
					Point expected = mouse[i];
					assertEquals( "Unexpected data on square "+i, expected, actual );
				}
				// 3. (menu: undo all)[!undo redo]
				for (int i=0; i<mouse.length-1; i++) {
					undo.doClick();
					assertTrue ( "'Undo' menu should be enabled", undo.isEnabled() );
					assertTrue ( "'Redo' menu should be enabled", redo.isEnabled() );
					assertEquals( "Undo stack should have commands", mouse.length-i-1, frame.getUndo().getSize() );
					assertEquals( "Redo stack should have commands", i+1,              frame.getRedo().getSize() );
				}
				undo.doClick();
				assertFalse ( "'Undo' menu should be disabled", undo.isEnabled() );
				assertTrue  ( "'Redo' menu should be enabled",  redo.isEnabled() );
				assertEquals( "Undo stack should be empty",      0,            frame.getUndo().getSize() );
				assertEquals( "Redo stack should have commands", mouse.length, frame.getRedo().getSize() );
				// 4. (get squares)[is empty]
				squares = frame.getSquares();
				assertEquals( "Stack of squares should be empty", 0, squares.getSize() );
				// 5. (menu: redo all)[undo !redo]
				for (int i=0; i<mouse.length-1; i++) {
					redo.doClick();
					assertTrue ( "'Undo' menu should be enabled", undo.isEnabled() );
					assertTrue ( "'Redo' menu should be enabled", redo.isEnabled() );
					assertEquals( "Undo stack should have commands", i+1,              frame.getUndo().getSize() );
					assertEquals( "Redo stack should have commands", mouse.length-i-1, frame.getRedo().getSize() );
				}
				redo.doClick();
				assertTrue  ( "'Undo' menu should be enabled",  undo.isEnabled() );
				assertFalse ( "'Redo' menu should be disabled", redo.isEnabled() );
				assertEquals( "Undo stack should have commands", mouse.length, frame.getUndo().getSize() );
				assertEquals( "Redo stack should be empty",      0,            frame.getRedo().getSize() );
				// 6. (get squares)[equal copy]
				squares = frame.getSquares();
				assertEquals( "Stack of squares should have several squares", mouse.length, squares.getSize() );
				List<Point> expected = new ArrayList<>( Arrays.asList( mouse )); 
				for (ColoredSquare s : squares) {
					assertEquals( "Unexpected square", expected.remove(0), s.getLocation() );
				}
			}
		});
	}
}