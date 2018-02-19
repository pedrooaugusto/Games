/*
	Pedro Augusto, 2018 (c)
	***********************
	Cria todas as peaças do quebra cabeça
*/

const concavity = () => Math.random() >= 0.5 ? 1 : -1;

const createPieces = function(PIECE_SIZE, NUM_ROWS, NUM_COLS) {
	const pieces = [];
	for(let i = 0; i < NUM_COLS; i++)
    {
    	for(let j = 0; j < NUM_ROWS; j++)
    	{
    		const c = [0, 1, 2, 3].map(() => concavity());

    		if(j - 1 >= 0)
    			c[0] = pieces[NUM_ROWS * i + (j-1)].p[2] * -1;
    		if(i - 1 >= 0)
    			c[3] = pieces[NUM_ROWS * (i-1) + j].p[1] * -1;
    		if(i === 0) c[3] = 0;
    		if(j === 0) c[0] = 0;
    		if(i === NUM_COLS - 1) c[1] = 0;
    		if(j === NUM_ROWS - 1) c[2] = 0;

    		pieces.push(new Piece(i, j, PIECE_SIZE, c));
    	}
    }
    return pieces;
}