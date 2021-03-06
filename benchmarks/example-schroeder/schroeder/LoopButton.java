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

public class LoopButton
		extends JToggleButton
		implements ActionListener, EnableDisableable
	{
	public static LoopButton instance() {
		if( instance_ == null ) {
			instance_ = new LoopButton();
		}
		
		return( instance_ );
	}
	
	private LoopButton() {
		super( ImageIconFactory.instance().getImageIcon( "/images/loop.gif" ) );
		setToolTipText( "Loop" );
		Dimension size = new Dimension( 27, 27 );
		setPreferredSize( size );
		setMaximumSize( size );
		
		addActionListener( this );

		ActionManager.instance().add( this );
	}

	public void enableDisable( SoundWindow active ) {
		setEnabled( active != null );
		setSelected( active != null && active.getModel().isLoop() );
	}
	
	public void actionPerformed( ActionEvent evt ) {
		SoundModel model = Desktop.instance().getActiveWindow().getModel();
		model.setLoop( isSelected() );
	}
	
	private static LoopButton instance_ = null;
}
