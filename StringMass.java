

import java.awt.*;

public class StringMass extends BufferedApplet {
	private static final long serialVersionUID = 1L;
	
	public StringMass(){
	}
	int preset = 0;
	int N = 9, r = 50;
	double t, tfactor, tmin = 1.0, tmax = 10.0, t0 = System.currentTimeMillis();
	double  y0 = 400.0, l = 1000.0, dx = l/N;
	double T, Tmin = 5000.0, Tmax = 50000.0, d = 1.0, m, mMin = 1.0, mMax = 10.0, grav = 0.0, c = 1.0;
	double k = Tmin/(dx*m);

	int[] x  = new int[N];
	double[]  y  = new double[N];
	double[] dy = new double[N];
	double[]  a = new double[N];
	
	Mass[] Masses = new Mass[N - 2];
	StringThing string = new StringThing(x, y0, y, dy, a);
	{
	for (int n = 0; n < N; n++) {
		x[n] = (int) (25 + n*dx);
			
		a[n] = 0;
		switch (preset) {
			case 0:	
				if (n < N/2 + N%2){
					dy[n] = (n == 0 || n == N - 1 ? 0 : 10*n);
					dy[N-(n+1)] = (n == 0 || n == N - 1 ? 0 : 10*n);
				}
				y[n] = (int) (y0 - dy[n]);
				break;
			case 1: if (n <= N/4) {
				dy[n] = (n == 0 || n == N - 1 ? 0 : -10*n);
				dy[N/2-n] = (n == 0 || n == N - 1 ? 0 : -10*n);
				dy[n + N/2] = (n == 0 || n == N - 1 ? 0 : 10*n);
				dy[N-(n+1)] = (n == 0 || n == N - 1 ? 0 : 10*n);
				}
				y[n] = (int) (y0 - dy[n]);
				break;
			case 2:
				if (N%2 == 0){
					dy[n] = (n == N/2 ? 10*N : (n == N/2 + 1 ? -10*N : 0));
				}
				else {
					dy[n] = (n == N/2 ? 10*N : 0);
				}
				y[n] = (int) (y0 - dy[n]);
				break;
			case 3:
				dy[n] = (n%2 == 1 ? -100 : 100);
				y[n] = y0;
				break;
			case 4:
				if (N%2 == 1) {
					if (n < N/2 + N%2){
						dy[n] = (n == 0 || n == N - 1 ? 0 : (n%2 == 1 ? 10*n : -10*n));
						dy[N-(n+1)] = (n == 0 || n == N - 1 ? 0 : (n%2 == 1 ? 10*n : -10*n));
					}
				}
				else {
					if (n < N/2 + N%2){
						dy[n] = (n == 0 || n == N - 1 ? 0 : (n%2 == 1 ? 10*n : -10*n));
						dy[N-(n+1)] = (n == 0 || n == N - 1 ? 0 : (n%2 == 1 ? -10*n : 10*n));
					}
				}
				y[n] = (int) (y0 - dy[n]);
				break;
			case 5:
				dy[n] = (n == 1 ? 100 : (n ==  N - 2? -100 : 0));
				y[n] = (int) (y0 - dy[n]);
				break;
		}
	}
	for (int n = 0; n < N - 2; n++) {
		Masses[n] = new Mass(m, x[n+1], y[n+1], dy[n+1], a[n+1]);
	} 
	}
	int keyN = 6, index = -1;
	
	boolean clickedDown, clickedUp, spaceDown, spaceUp, rUp, rDown, gUp, gDown, iUp, iDown, plusUp, plusDown, minusUp, minusDown;
	boolean[] nDown = new boolean[keyN];
	boolean[] nkeyUp = new boolean[keyN];
	boolean play = false, gravity = false, interact = false, drag = false, SlowMotion = false;
	
	Font fontLarge = new Font("Helvetica", Font.BOLD, 28); 
	Font font = new Font("Verdana", Font.BOLD, 16);
	
	int activeX, activeY;
	
	Play playButton = new Play(10, 10, 50, 50);
	Switch gravityButton = new Switch(70, 10, 150, 50, "Gravity");
	Switch interactButton = new Switch(10, 70, 150, 25, "Interact");
	
	VariableAdjust mass = new VariableAdjust(230, 10, 200, 50);
	VariableAdjust Tension = new VariableAdjust(440, 10, 250, 50);
	VariableAdjust SlowMo = new VariableAdjust(170, 70, 225, 50);
	{SlowMo.resetSlider();}
	
	Button Reset = new Button(850, 10, 100, 50);
	Button Nminus = new Button(700, 40, 60, 20);
	Button Nplus  = new Button(770, 40, 60, 20);
	Button[] presets = new Button[6];
	{
	for (int n = 0; n < keyN; n++) {
		presets[n] = new Button(960 + n%3*30, 10 + 30*(n/3), 20, 20);
		nDown[n] = false;
		nkeyUp[n] = false;
	}
	}
	
	Sign Nsign = new Sign("No. Mass: " + (N - 2), 700, 10, 130, 20);
	Sign presetSign = new Sign("Presets", 960, 70, 80, 20);
	
	
	public void render(Graphics g) {
		int w = getWidth(), h = getHeight();
		
		clickedDown = ! wasMouseDown && mouseDown;
	    clickedUp = wasMouseDown && ! mouseDown;
	    
	    spaceDown = ! wasKeyDown[' '] && keyDown[' ']; 
	    spaceUp =  wasKeyDown[' '] && ! keyDown[' '];
		
	    rDown = ! wasKeyDown['r'] && keyDown['r']; 
	    rUp =  wasKeyDown['r'] && ! keyDown['r'];
	    
	    gDown = ! wasKeyDown['g'] && keyDown['g']; 
	    gUp =  wasKeyDown['g'] && ! keyDown['g'];
		
	    plusDown = ! wasKeyDown['='] && keyDown['=']; 
	    plusUp =  wasKeyDown['='] && ! keyDown['='];
	    
	    minusDown = ! wasKeyDown['-'] && keyDown['-']; 
	    minusUp =  wasKeyDown['-'] && ! keyDown['-'];
	     
	    iDown = ! wasKeyDown['i'] && keyDown['i']; 
	    iUp =  wasKeyDown['i'] && ! keyDown['i'];
	    
		if (clickedDown) {
			requestFocusInWindow();
			activeX = mouseX;
			activeY = mouseY;
		}
		
		g.setColor(Color.white);
		g.fillRect(0, 0, w, h );
		
		g.setColor(Color.blue);
		g.drawLine(0, (int) y0, w, (int) y0);
		
		
		for (int n = 0; n < keyN; n++) {
			char nChar = ((n+1) + "").charAt(0);
			nDown[n] = keyDown[nChar]; 
		    nkeyUp[n] =  wasKeyDown[nChar] && ! keyDown[nChar];
		    
			presets[n].draw(g, mouseDown, activeX, activeY, nDown[n]);	
			presets[n].label(g, "" + (n + 1), font, 0, 3);
			if (presets[n].behavior(mouseX, mouseY, activeX, activeY, clickedUp, nkeyUp[n])) {
				preset = n;
				refresh();
			}
		}
		
		playButton.draw(g, mouseDown, activeX, activeY, keyDown[' ']);
		playButton.Icon(g, play);
		if (playButton.behavior(mouseX, mouseY, activeX, activeY, clickedUp, spaceUp)) {
			play = (play ? false : true);
			if (play) {
				interact = false;
			}
		}
		
		gravityButton.draw(g, mouseDown, activeX, activeY, keyDown['g']);
		gravityButton.Label(g, gravity);
		if (gravityButton.behavior(mouseX, mouseY, activeX, activeY, clickedUp, gUp)) {
			gravity = (gravity ? false : true);
			grav = (gravity ? 98.10 : 0);
		}
		
		interactButton.draw(g, mouseDown, activeX, activeY, keyDown['i']);
		interactButton.Label(g, interact);
		if (interactButton.behavior(mouseX, mouseY, activeX, activeY, clickedUp, iUp)) {
			interact = (interact ? false : true);
			if (interact) {
				activeX = -1;
				activeY = -1;
				play = false;
			}
		}
		
		SlowMo.draw(g);
		SlowMo.label(g, "(dt/c)  c", tfactor, "", 0);
		tfactor = tmin + (tmax - tmin)*SlowMo.behavior(mouseX, activeX, activeY, clickedUp, clickedDown, wasMouseDown, mouseDown);
		
		presetSign.draw(g, font, 5, 3);
		
		mass.draw(g);
		mass.label(g, "Mass", m, "kg", 30);
		m = mMin + (mMax - mMin)*mass.behavior(mouseX, activeX, activeY, clickedUp, clickedDown, wasMouseDown, mouseDown);
		
		Tension.draw(g);
		Tension.label(g,"Tension", T/Tmin, "N", 20);
		T = Tmin + (Tmax - Tmin)*Tension.behavior(mouseX, activeX, activeY, clickedUp, clickedDown, wasMouseDown, mouseDown);
		
		k = T/(dx*m);
		
		Reset.draw(g, mouseDown, activeX, activeY, keyDown['r']);
		Reset.label(g, "Reset", fontLarge, 5, 5);
		if (Reset.behavior(mouseX, mouseY, activeX, activeY, clickedUp, rUp)) {
			refresh();
		}
		
		Nsign.draw(g, font, 4, 4);
		
		Nplus.draw(g, mouseDown, activeX, activeY, keyDown['=']);
		Nplus.label(g, "+", fontLarge, 12, 2);
		if (Nplus.behavior(mouseX, mouseY, activeX, activeY, clickedUp, plusUp)) {
			N += (N  - 2 < 40 ? 1 : 0);
			refresh();
		}
		
		Nminus.draw(g, mouseDown, activeX, activeY, keyDown['-']);
		Nminus.label(g, "-", fontLarge, 21, 2);
		if (Nminus.behavior(mouseX, mouseY, activeX, activeY, clickedUp, minusUp)) {
			N += (N - 2 > 1 ? -1 : 0);
			refresh();
		}
		
		t = (System.currentTimeMillis() - t0)/1000.0;
		t = t/(tfactor);
		if (interact) {
			string.interact(mouseY, index, mouseDown, wasMouseDown, drag);
		}
		for (int n = 0; n < N - 2; n++) {
			Masses[n].draw(g, m);
			if (play) {
				Masses[n].physics(k, t, y0 - y[n], y0 - y[n+1], y0 - y[n+2], grav);
			}
			if (interact) {
				if (Masses[n].contains(activeX, activeY)) {
					Masses[n].setDy(0.0);
					drag = true;
					index = n + 1;
				}
				if (clickedUp) {
					drag = false;
				}
				if (drag && n == index - 1) {
					Masses[n].setY(y[n+1]);
				}
				System.out.println(n + ": " + Masses[n].contains(activeX, activeY));
				//System.out.println(drag +", " + n + "="+ index + "   " + y[n]);
			}
		}
		if (clickedUp) {
			drag = false;
		}
		string.draw(g);
		if (play) {
			string.physics(k, t, grav);
		}
		
		t0 = System.currentTimeMillis();
		animating = true;
	}
	public void refresh(){
		play = false;
		
		dx = l/N;
		x  = new int[N];
		y  = new double[N];
		dy = new double[N];
		a = new double[N];
		
		Masses = new Mass[N - 2];
		string = new StringThing(x, y0, y, dy, a);
		Nsign = new Sign("No. Mass: " + (N - 2), 700, 10, 130, 20);
		
		for (int n = 0; n < N; n++) {
			x[n] = (int) (25 + n*dx);
				
			a[n] = 0;
			switch (preset) {
				case 0:	
					if (n < N/2 + N%2) {
						dy[n] = (n == 0 || n == N - 1 ? 0 : 10*n);
						dy[N-(n+1)] = (n == 0 || n == N - 1 ? 0 : 10*n);
						}
					y[n] = (int) (y0 - dy[n]);
					break;
				case 1: 
					if (n <= N/4) {
						dy[n] = (n == 0 || n == N - 1 ? 0 : -10*n);
						dy[N/2-n] = (n == 0 || n == N - 1 ? 0 : -10*n);
						dy[n + N/2] = (n == 0 || n == N - 1 ? 0 : 10*n);
						dy[N-(n+1)] = (n == 0 || n == N - 1 ? 0 : 10*n);
						}
					y[n] = (int) (y0 - dy[n]);
					break;
				case 2:
					if (N%2 == 0){
						dy[n] = (n == N/2 ? 10*N : (n == N/2 - 1 ? -10*N : 0));
					}
					else {
						dy[n] = (n == N/2 ? 10*N : 0);
					}
					y[n] = (int) (y0 - dy[n]);
					break;
				case 3:
					dy[n] = (n == 1 ? 100 : (n ==  N - 2? -100 : 0));
					y[n] = (int) (y0 - dy[n]);
					break;
				case 4:
					if (N%2 == 1) {
						if (n < N/2 + N%2){
							dy[n] = (n == 0 || n == N - 1 ? 0 : (n%2 == 1 ? 10*n : -10*n));
							dy[N-(n+1)] = (n == 0 || n == N - 1 ? 0 : (n%2 == 1 ? 10*n : -10*n));
						}
					}
					else {
						if (n < N/2 + N%2){
							dy[n] = (n == 0 || n == N - 1 ? 0 : (n%2 == 1 ? 10*n : -10*n));
							dy[N-(n+1)] = (n == 0 || n == N - 1 ? 0 : (n%2 == 1 ? -10*n : 10*n));
						}
					}
					y[n] = (int) (y0 - dy[n]);
					break;
				case 5:
					int randy = -N + (int)(Math.random() * ((N + N) + 1));
					dy[n] = (n == 0 || n == N - 1 ? 0 : randy*10);
					y[n] = (int) (y0 - dy[n]);
					break;
			}
		}
		for (int n = 0; n < N - 2; n++) {
			Masses[n] = new Mass(m, x[n+1], y[n+1], dy[n+1], a[n+1]);
		} 
	}
}