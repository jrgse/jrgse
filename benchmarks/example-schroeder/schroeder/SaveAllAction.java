/*
    Schroeder 0.2 (Development Release 2)
    Copyright (C) 1998 David Veldhuizen <david@interlog.com>

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
*/


package schroeder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SaveAllAction
		extends AbstractAction
		implements EnableDisableable
	{
	public static SaveAllAction instance() {
		if( instance_ == null ) {
			instance_ = new SaveAllAction();
		}
		
		return( instance_ );
	}
	
	private SaveAllAction() {
		super(
			"Save All",
			ImageIconFactory.instance().getImageIcon( "/images/blank.gif" )
		);
		
		ActionManager.instance().add( this );
	}

	public void enableDisable( SoundWindow active ) {
		// ActionManager.REQUIRES_ANY_DIRTY_MODEL
		setEnabled(
				active != null
			&&	active.getModel().isDirty()
		);
	}
	
	public void actionPerformed( ActionEvent evt ) {
		MainFrame.instance().setCursor(
			Cursor.getPredefinedCursor(
				Cursor.WAIT_CURSOR
			)
		);

		try {
			JInternalFrame[] windows = Desktop.instance().getAllFrames();
			for( int i=0; i<windows.length; i++ ) {
				SoundWindow window = (SoundWindow) windows[ i ];
				if( window.getModel().isDirty() ) {
					SaveAction.instance().save( window );
				}
			}
			//ActionManager.instance().enableDisableActions();
			MainPanel.instance().repaint();
		}
		catch( Exception e ) {
			if( schroeder.Constants.DEBUG ) {
				e.printStackTrace();
			}
		}
		
		MainFrame.instance().setCursor(
			Cursor.getDefaultCursor()
		);
	}
	
	private static SaveAllAction instance_ = null;
}
