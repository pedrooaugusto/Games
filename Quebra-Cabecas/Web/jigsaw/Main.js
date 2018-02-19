const PIECE_SIZE = 120*0.5;
const NUM_ROWS = 5;
const NUM_COLS = 10;

const start = function(){
	const canvas = document.getElementById('myCanvas');
	const ctx = canvas.getContext('2d');

	// new Piece(1, 1, PIECE_SIZE, [1, 1, 1, 1]), new Piece(2, 1, PIECE_SIZE, [1, 1, 1, -1])
	
	const pieces = createPieces(PIECE_SIZE, NUM_ROWS, NUM_COLS);
	const img = new Image();
    img.src = 'images/Symphaty_by_Briton.jpg';
    img.onload = function (){
    	const pattern = ctx.createPattern(img, 'no-repeat');
    	ctx.lineWidth = "2";

    	// ctx.drawImage(img, 0, 0, 600, 300);

    	// ctx.drawImage(img, 0, 0, 600, 600, 605, 0, 100, 100);
		// ctx.fillStyle = pattern;
		addEvents();
        draw();
    }
 
	const draw = (x, y) => {
    	const focused = []
    	for(let piece of pieces)
    		if(!piece.focus && !piece.float)
    			piece.draw(ctx, img, x, y);
    		else
    			focused.push(piece);
    	for (let piece of focused)
    		if(piece.focus)
    			piece.draw(ctx, img, x, y);
    	for (let piece of focused)
    		if(piece.float)
    			piece.draw(ctx, img, x, y);
	}

	const addEvents = () => {
		canvas.addEventListener('mousemove', (evt) => {
			ctx.clearRect(0, 0, 1200, 600);
			draw(evt.x, evt.y);
			for(let piece of pieces)
				piece.mouseOver(evt.x, evt.y);
		});		
		canvas.addEventListener('click', (evt) => {
			for(let piece of pieces)
				piece.mouseClick(evt.x, evt.y);
		});
	}
}

start();