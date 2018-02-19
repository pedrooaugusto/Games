/*
	Pedro Augusto, 2018 (c)
	***********************
	Representa uma peça do quebra cabeça
*/
class Piece{
	constructor(row, col, size, p){
		this.row = row;
		this.col = col;
		this.size = size;
		this.p = p;
		this.focus = false;
		this.float = false;
	}

	toString(){
		console.log(this.row + " " + this.col + " " + this.p);
	}

	draw(ctx, pattern, x__, y__)
	{
		let x = this.row * this.size;
		let y = this.col * this.size;
		ctx.save();
		if(this.float)
		{
			x__ = (x__ - x) - this.size / 2;
			y__ = (y__ - y) - this.size / 2;
			ctx.translate(x__, y__);
		}
		const moveSize = (this.size / 3) * (this.float ? 0.8 : 1);
		ctx.beginPath();

		ctx.strokeStyle = this.focus ? "red" : this.float ? "blue" : "white";

		ctx.moveTo(x, y);//350, 400

		ctx.lineTo(x + moveSize, y);//400, 400

		x += moveSize;//x = 400
		this.drawCurve(ctx, x, y, moveSize, this.p[0], "H");

		x += moveSize * 2;//500
		ctx.lineTo(x, y);

		y += moveSize;//450
		ctx.lineTo(x, y);

		this.drawCurve(ctx, x, y, moveSize, this.p[1], "V");

		y += moveSize * 2;//550
		ctx.lineTo(x, y);

		x -= moveSize;//450
		ctx.lineTo(x, y);

		this.drawCurve(ctx, x, y, -moveSize, this.p[2], "H");

		x -= moveSize * 2;//350
		ctx.lineTo(x, y);

		y -= moveSize;//500
		ctx.lineTo(x, y);

		this.drawCurve(ctx, x, y, -moveSize, this.p[3], "V");

		y -= moveSize * 2;
		ctx.lineTo(x, y);


		ctx.stroke();
    	ctx.fill();
    	ctx.clip();

    	ctx.drawImage(img, 0, 0, NUM_COLS*PIECE_SIZE, NUM_ROWS*PIECE_SIZE);
		
		ctx.closePath();

		ctx.restore();	
	}

	drawCurve(ctx, x, y, moveSize, type, dir)
	{
		if(type === 0) /* No curve, rect */
		{
			if(dir === "H")
				ctx.lineTo(x + moveSize, y);
			else
				ctx.lineTo(x, y + moveSize);
		}
		if(type === 1)
		{
			const hmid = x + moveSize * 0.5;//325 
			const vmid = y + moveSize * 0.5;//475
			if(dir === "H")
				ctx.quadraticCurveTo(hmid, y - moveSize, 
					x + moveSize, y);
			else
				ctx.quadraticCurveTo(x + moveSize, 
					vmid, x, y + moveSize);
		}
		if(type === -1)
		{
			const hmid = x + moveSize * 0.5;
			const vmid = y + moveSize * 0.5;
			if(dir === "H")
				ctx.quadraticCurveTo(hmid, y + moveSize, 
					x + moveSize, y);
			else
				ctx.quadraticCurveTo(x - moveSize, 
					vmid, x, y + moveSize);	
		}
	}

	onMe(x, y)
	{
		const x_ = this.row * this.size;
		const y_ = this.col * this.size;
		const moveSize = this.size;
		return ((x >= x_ && x <= x_ + moveSize) && (y >= y_ && y <= y_ + moveSize));
	}

	mouseOver(x, y)
	{
		if(this.onMe(x, y))
			this.focus = true;
		else
			this.focus = false;
	}

	mouseClick(x, y)
	{
		if(this.onMe(x, y)){
			this.float = !this.float;
		}
	}
}