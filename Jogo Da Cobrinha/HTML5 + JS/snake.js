
//Inicializando as variáeis globais

//ctx == context; onde eu desenho as coisas
var canvas, gameLoop, cobra, ctx, moeda, retry, header, colisao, 
	tempoMoeda, animation, controles, HEIGHT, WIDTH;
var info = "Movimente-se usando as setas ou os botões!";
var valoresPC = {
	tipo: "PC",
	info: 20,
	cobra:{
		dimensao: {x: 0.03, y: 0.03}
	},
	partes:{
		dimensao:{x: 0.0125, y: 0.0125}
	},
	moeda:{
		dimensao:{x: 0.02, y:0.02},
		raio: 0.01,
		movimento:{
			trig: {sinDivide: 20, cosDivide: 15},
			amplitude: 20,
			top: 10,
			alguns: [[30, 20, 35, 15], [20, 40, 45, 14],
				[60, 30, 50, 15], [70, 80, 50, 15], 
				[60, 80, 60, 20]]
		}
	},
	animation:{
		loseAlert:{
			box:{
				dimensao: {x: 0.3, y: 0.1},
				posicao: {x: 0.3/2, y: 0.2}			
			},
			texto:{
				posicao: {x: 0, y: 0.2+(0.6*0.1)},
				font: 0.02
			}
		}
	},
	header:{
		dimensao:{x: 1, y: 0.1},
		pontos:{
			posicao:{x: 0, y: 0},
			font: 0.02
		},
		info:{
			posicao:{x: 0, y: 0}
		}
	},
	controles:{
		dimensao:{x: 0.05, y: 0.05},
		up:{x: 0.5 - (0.05*0.5), y: 0.6},
		down:{x: 0.5 - (0.05*0.5), y: 0.83},
		left:{x: (0.5 - (0.05*0.5)) - 0.05, y: (0.6 + (1-0.6)/2) - 0.09},
		right:{x: 1 - ((0.5 - (0.05*0.5))), y: (0.6 + (1-0.6)/2) - 0.09},
	}
};
var valoresMobile = {
	tipo: "Mobile",
	info: 14,
	cobra:{
		dimensao: {x: 0.06, y: 0.06}
	},
	partes:{
		dimensao:{x: 0.025, y: 0.025}
	},
	moeda:{
		dimensao:{x: 0.04, y:0.04},
		raio: 0.02,
		movimento:{
			trig: {sinDivide: 20, cosDivide: 15},
			amplitude: 12,
			top: 10,
			alguns: [[30, 30, 12, 10], [20, 40, 15, 12],
				[60, 10, 11, 11], [50, 80, 9, 15], 
				[20, 80, 15, 10]]
		}
	},
	animation:{
		loseAlert:{
			box:{
				dimensao: {x: 0.7, y: 0.1},
				posicao: {x: 0.7/2, y: 0.2}			
			},
			texto:{
				posicao: {x: 0, y: 0.2+(0.6*0.1)},
				font: 0.05
			}
		}
	},
	header:{
		dimensao:{x: 1, y: 0.1},
		pontos:{
			posicao:{x: 0, y: 0},
			font: 0.05
		},
		info:{
			posicao:{x: 0, y: 0}
		}
	},
	controles:{
		dimensao:{x: 0.16, y: 0.16},
		up:{x: 0.5 - (0.16*0.5), y: 0.6},
		down:{x: 0.5 - (0.16*0.5), y: 1 - 0.16},
		left:{x: (0.5 - (0.16*0.5)) - 0.16, y: (0.6 + (1-0.6)/2) - 0.09},
		right:{x: (0.5 - (0.16*0.5)) + 0.16, y: (0.6 + (1-0.6)/2) - 0.09},
	}
}
var currentValores;
function Cobra()
/*
	O objeto cobra
	este objeto é em si a cabeça da cobra
	e possui um campo chamado parte que é
	na verdade a calda.
*/
{
	this.nome = "Cobra";
	this.partes = new Array();
	this.dimensao = {x: currentValores.cobra.dimensao.x*WIDTH, y: currentValores.cobra.dimensao.y*WIDTH};
	this.posicao = {x: this.dimensao.x, y: (HEIGHT*currentValores.header.dimensao.y)+this.dimensao.y*5};
	this.perdeSeMorrer = {//Cuida de quantas unit a cobra perde se o tempo acabar
		atual : -1, att: function(){this.atual = Math.floor((cobra.partes.length)/2);}
	};
	this.velocidade = {x: this.dimensao.x, y: 0, padrao: this.dimensao.x};
	this.cor = "#C51212";
	this.draw = function ()//Desenhar cobra 
	{
		ctx.beginPath();
			ctx.shadowBlur = 2;
			ctx.shadowColor = "white";
			ctx.fillStyle = this.cor;
			ctx.fillRect(this.posicao.x, this.posicao.y, this.dimensao.x, this.dimensao.y);
		ctx.closePath();
		for (var i = 0; i < this.partes.length; i++)//Desenhar os filhos
			this.partes[i].draw();
	};
	this.killSomeChild = function()
	{
		for(var i = 0; i < this.perdeSeMorrer.atual; i++)
		{
			this.partes.pop(this.partes[i]);
		}
		/*
			Abaixo temos gambiarra
			Se a cobra pegasse uma unit no meio da animação
			loseSomeChilds() essa unit era incluida no final do vetor.
			O problema é que depois que a loseSomeChilds() acaba
			ela chama o killSomeChilds() para tirar os últimos x do vetor
			só que a unit que ela acabou de pegar acaba sendo morta tbm
			o que não era pra acontecer...
			Então depois eu pinto todo mundo de branco de novo...		
		*/
		for (var i = 0; i < this.partes.length; i++)
		{
			this.partes[i].cor = "white";
			this.partes[i].enabled = true;
		}
		this.perdeSeMorrer.att();
		//moeda.changeMovement();
	};
	this.move = function()//Move-se cabeça e depois a calda 
	{
		this.movimentoContinuo();
		this.movimentarFilhos();
	}
	this.movimentoContinuo = function()//Movimentando a cabeça
	{
		this.posicao.x += this.velocidade.x;
		this.posicao.y += this.velocidade.y;
	};
	this.movimentarFilhos = function()
	/*
		Reorganiza a posição de todos dando ideia de que todos
		cabeça e calda se movem quando na verdade só a calda
		se cabeça se move, os filhos(calda) apenas copiam o movimento
	*/
	{
		var auX = this.posicao.x + (this.dimensao.x/2)  -  this.partes[0].dimensao.x/2,
			 auY = this.posicao.y + (this.dimensao.y/2) - this.partes[0].dimensao.y/2, ggX, ggY;
		for (var i = 0; i < this.partes.length; i++) 
		{
			ggX = this.partes[i].posicao.x;
			ggY = this.partes[i].posicao.y ;
			this.partes[i].posicao.x = auX;
			this.partes[i].posicao.y = auY;
			auX = ggX;
			auY = ggY;
		}
	}
	this.turn = 
	{
		choose: function(where)
		{
			var vxy = {};
			if(where == 38)
				vxy = cobra.turn.top();
			if(where == 39)
				vxy = cobra.turn.right();
			if(where == 40)
				vxy = cobra.turn.bottom();
			if(where == 37)
				vxy = cobra.turn.left();
			if(vxy != null){
				cobra.velocidade.x = vxy.x;
				cobra.velocidade.y = vxy.y;	
			};
		},
		left: function()
		{
			if(!(cobra.velocidade.x > 0))
				return {x: cobra.velocidade.padrao*(-1), y: 0};
		},
		right: function()
		{
			if(!(cobra.velocidade.x < 0))
				return {x: cobra.velocidade.padrao, y: 0};
		},
		top: function()
		{
			if(!(cobra.velocidade.y > 0))
				return {x: 0, y: cobra.velocidade.padrao*(-1)};
		},
		bottom: function()
		{
			if(!(cobra.velocidade.y < 0))
				return {x: 0, y: cobra.velocidade.padrao};
		}
	}
}
function Animation()
{
	/*
		Foi criada com intuito de colocar algumas animações
		e exibir informações na tela por algum tempo
	*/
	this.segundosPast = {past:0, padrao: 2};
	this.loop = null;
	this.loseIsAlive = false;
	this.drawYouLoseBecauseLoseSomeChilds = function()
	{
		ctx.beginPath();
			ctx.shadowBlur = -1;
			ctx.shadowColor = "black";
			ctx.fillStyle = "#4d2c9c";
			ctx.fillRect(WIDTH/2-currentValores.animation.loseAlert.box.posicao.x*WIDTH, 
				currentValores.animation.loseAlert.box.posicao.y*HEIGHT, 
				currentValores.animation.loseAlert.box.dimensao.x*WIDTH, 
				currentValores.animation.loseAlert.box.dimensao.y*HEIGHT);
		ctx.closePath();
		ctx.beginPath();
			ctx.shadowBlur = 3;
			ctx.shadowColor = "lightgray";
			ctx.fillStyle = "white";
			ctx.font = "bold "+currentValores.animation.loseAlert.texto.font*WIDTH+"px Segoe UI Light";
			//URL the beatles 'jA6Z1QJA6EA'
			var texto = "You're going to lose this girl!";
			ctx.fillText(texto, WIDTH/2 - ctx.measureText(texto).width/2, 
				currentValores.animation.loseAlert.texto.posicao.y*HEIGHT);
			ctx.shadowBlur = 0;
			ctx.shadowColor = "white";
			ctx.fillStyle = "white";
			ctx.font = (currentValores.animation.loseAlert.texto.font*WIDTH)*0.5+"px Segoe UI Light";
			ctx.fillText("youtu.be/jA6Z1QJA6EA", 
				WIDTH/2 - currentValores.animation.loseAlert.box.posicao.x*WIDTH, 
				currentValores.animation.loseAlert.box.posicao.y*HEIGHT-2);
		ctx.closePath();
	};
	this.draw = function()
	{
		if(this.loseIsAlive)
			this.drawYouLoseBecauseLoseSomeChilds();
	}
	this.controleLoseSomeChilds = function()
	{
		if(this.segundosPast.past == this.segundosPast.padrao)
		{
			clearTimeout(this.loop);
			this.segundosPast.past = 0;
			this.loop = null;
			this.loseIsAlive = false;
			cobra.killSomeChild();
		}
		else
			this.segundosPast.past += 1;
	}
	this.youLoseStart = function()
	{
		this.loseIsAlive = true;
		for (var i = cobra.partes.length-1; i >= cobra.partes.length - cobra.perdeSeMorrer.atual; i--)
			cobra.partes[i].enabled = false;
		this.loop = setInterval("animation.controleLoseSomeChilds()", 1000);
	}
}
function Partes()//Partes que compeem a cobra
{
	this.nome = "Parte";
	this.numero = 42;
	this.posicao = {x: 42, y: 42};
	this.dimensao = {x:currentValores.partes.dimensao.x*WIDTH, y: currentValores.partes.dimensao.y*WIDTH};
	this.cor = "white";
	this.enabled = true;
	this.draw = function()
	{
		ctx.beginPath();
			ctx.shadowBlur = 2;
			ctx.shadowColor = "white";
			ctx.fillStyle = this.cor = this.prestesMorrer();
			ctx.fillRect(this.posicao.x, this.posicao.y, this.dimensao.x, this.dimensao.y);
		ctx.closePath();
	},
	this.prestesMorrer = function()
	{
		if(this.enabled)
			return "white";
		else
			if(this.cor=="white")
				return "#2E1961";
			else
				return "white";
	}
}
function Moeda()//O 'negocio' que a cobra precisa 'comer' para ficar grande
{
	this.nome = "Moeda";
	this.numero = 42;
	this.posicao = {x: 75, y: 100};
	this.originalPosition = {x: WIDTH/2, y:HEIGHT/2};
	this.dimensao = {x: currentValores.moeda.dimensao.x*WIDTH, y: currentValores.moeda.dimensao.y*WIDTH};
	this.cor = "rgba(243, 221, 30, 1)";
	this.raio = currentValores.moeda.raio*WIDTH;
	this.trig = currentValores.moeda.movimento.trig;
	/*Só um lugar onde guardar o top current*/
	this.consta = 0;
	/*
		É como se fosse até onde a animação pode ir
		dada a sua posição original quanto maior
		mais ela pode se distanciar de la. Isso
		acaba influênciando na velocidade
	*/
	this.amplitude = currentValores.moeda.movimento.amplitude;
	this.generate = function()
	/*
		Gera uma moeda em uma posiçção aleatória.
		A posição aletória vai ser sempre um múltiplo de 30,
		assim ajuda a cobra a pegar moeda, já que a cobra
		anda de 30 em 30 unidades.
	*/
	{
		var c = cobra.velocidade.padrao;
		this.posicao.x = Math.round(Math.floor(Math.random()*(canvas.width - c))/c)*c;
		this.posicao.y = Math.round(Math.floor(Math.random()*(canvas.height - c))/c)*c;
		this.originalPosition.x = this.posicao.x;
		this.originalPosition.y = this.posicao.y;
	};
	this.track = function (x, y, top, ampl)
	/*
		Escrita por matemáticos para matemáticos, não sei como isso funciona
		Só sei que: y = sin(x), é furada
	*/
	{
		/*
			var top;
				velocidade com que o movimento é completado
				quanto maior mais rapido ele completa um ciclo
				quanto menor mais lentamente, se deixar muito
				rapido ele passa a apenas sumir de deixa de ser
				uma animacao...
		*/
       	var top = top + currentValores.moeda.movimento.top;
       	var x  = x + ampl * Math.sin(top / this.trig.sinDivide);
       	var y = (top / this.screenHeight < 0.65) ? y + 10 : 1 + y + ampl * Math.cos(top / this.trig.cosDivide);
       	if (x < 5)
       		x = 5;
       	if (x > canvas.width - 5)
       		x = canvas.width - 5;
       	if (y < HEIGHT*currentValores.header.dimensao.y)
       		y = HEIGHT*currentValores.header.dimensao.y;
       	if (y > canvas.height - 5)
       		y = canvas.height - 5;
       	return {x, top, y};
    };
	this.someAnimation = function()
	/*
		Me diz as novas coordenadas com base na função acima
	*/
	{
		var coisas = this.track(this.originalPosition.x, this.originalPosition.y,
			this.consta, this.amplitude);
		this.posicao.x = coisas.x;
		this.posicao.y = coisas.y;
		this.consta = coisas.top;
	};
	this.changeMovement = function()
	{
		/*var seno, coseno, top, amplitude;//
		seno = Math.floor(Math.random()*170)+10;
		coseno = Math.floor(Math.random()*170)+10;
		top = 16 + Math.floor(Math.random() * ((26 - 16) + 1));
		if(currentValores.tipo == "PC")
			amplitude = 26 + Math.floor(Math.random() * ((26 - 36) + 1));
		else
			amplitude = 9 + Math.floor(Math.random() * ((12 - 9) + 1));*/
		var n = (cobra.partes.length/4) - 1;
		n = (n < 0 ? 0 : Math.floor(n));
		this.trig.sinDivide = currentValores.moeda.movimento.alguns[n][0];
		this.trig.cosDivide = currentValores.moeda.movimento.alguns[n][1];;
		this.amplitude = currentValores.moeda.movimento.alguns[n][2];;
		currentValores.moeda.movimento.top =currentValores.moeda.movimento.alguns[n][3];;
	};
	this.draw = function()
	{
		this.someAnimation();
		ctx.beginPath();
			ctx.fillStyle = this.cor;
			ctx.shadowBlur = 8;
			ctx.shadowColor = "gold";
   			ctx.arc(this.posicao.x + this.dimensao.x*0.5, this.posicao.y + this.dimensao.x*0.5,
   					this.raio, 0, Math.PI*2, true);
    		ctx.fill();
		ctx.closePath();
	}
}
function Header()
{
	this.nome = "Header";
	this.posicao = {x: 0, y: 0};
	this.dimensao = {x:WIDTH, y: HEIGHT*currentValores.header.dimensao.y};
	this.cor = "black";
	this.draw = function()
	{
		ctx.beginPath();
			ctx.shadowBlur = 2;
			ctx.shadowColor = "white";
			ctx.fillStyle = this.cor;
			ctx.fillRect(this.posicao.x, this.posicao.y, this.dimensao.x, this.dimensao.y);
			ctx.font = currentValores.header.pontos.font*WIDTH+"px Segoe UI Light";
			ctx.shadowBlur = 0;
			ctx.fillStyle = "white";
			var texto = "Pontos: "+cobra.partes.length+"   "+
				tempoMoeda.getInfo();
			var tam = ctx.measureText(texto).width;
			ctx.fillText(texto, WIDTH/2 - tam/2, this.dimensao.y*0.7);
		ctx.closePath();
	}
}
function exibirCoordenadas(obj)
/*
	**PARAM:   O objeto que você deseja saber as coordenadas
	**RETORNA: Um texto com as coordenadas

	Para debug; exibe as coordenadas de um dado objeto na tela.
	Usado para saber onde a moeda foi parar, na fase de testes.
*/
{
	var cordenadas = obj.nome.toUpperCase() + " = { posicao: {x: "+obj.posicao.x+", y: "+
		obj.posicao.y+"}, dimensao: {x: "+obj.dimensao.x+", y: "+obj.dimensao.y+"}}";
	return cordenadas;
}
function loop()
/*
	Assim como todo game este também possui um loop infinito
	é onde tudo acontece, só que está bem compactado
*/
{
	canvas.limparTela();
	colisao.algumaColisao();
	animation.draw();
	cobra.move();
	cobra.draw();
	moeda.draw();
	header.draw();
	controles.draw();
	ctx.font = currentValores.info+"px Segoe UI Light";
	ctx.fillStyle = "white";
	ctx.fillText(info, WIDTH*0.99 - ctx.measureText(info).width, HEIGHT*0.99);
	if(gameLoop == null)
		gameOver();
}
function tempoParaPegarMoeda() 
{
	/*
		Cuida do time para pegar a moeda e tudo ele que faz de mais
	*/
	this.texto = "00:00";
	this.segundos = {segundos: 5, padrao:5};
	this.loop = null;
	this.realLoop = function()
	{
		this.segundos.segundos--;
		if (this.segundos.segundos < 0) 
		{
			this.segundos.segundos = this.segundos.padrao;
			moeda.generate();
			moeda.changeMovement();
			animation.youLoseStart();
		}
	}
	this.getInfo = function()
	{
		return "Left: "+this.segundos.segundos+"sec. to lose: "+cobra.perdeSeMorrer.atual+"uni.";
	}
	this.comecar = function()
	{
		this.segundos.segundos = this.segundos.padrao;
		this.loop = setInterval("tempoMoeda.realLoop()", 1000);
	}
	this.recomecar = function()
	{
		clearTimeout(this.loop);
		this.loop = null;
		this.comecar();
	}
}
function initComponents(w, h)
/*
	Inicia os componentes;
	Carrega as variaveis, começa o loop
	desenha um pouco da cobra, desenha uma moeda
	e adiciona os eventos do teclado na tela
*/
{
	WIDTH = w;
	HEIGHT = h;
	canvas = new Canvas();
	tempoMoeda = new tempoParaPegarMoeda();
	cobra = new Cobra();
	moeda = new Moeda();
	header = new Header();
	colisao = new tratarColisoes();
	animation = new Animation();
	controles = new Controles();
	var parte1 = new Partes();
	parte1.posicao.x = 210;
	parte1.posicao.y = 60;
	
	var parte2 = new Partes();
	parte2.posicao.x = 180;
	parte2.posicao.y = 60;
	
	var parte3 = new Partes();
	parte3.posicao.x = 150;
	parte3.posicao.y = 60;
	
	cobra.partes.push(parte1, parte2, parte3);
	cobra.perdeSeMorrer.att();
	ctx = canvas.objeto.getContext("2d");
	window.addEventListener("keydown", function (e) 
	{
		canvas.eventos.movimentoSeta(e);
	});
	window.addEventListener("keyup", function (e) {
		switch(e.keyCode)
		{
			case 37: case 38: case 39: case 40:
				canvas.keys[e.keyCode] = false;
			break;	
		}
	});
	//Retry button
		var texto = "RETRY";
		ctx.font = "bold 25px Segoe UI";
		retry = new Botao(WIDTH*0.425, HEIGHT*0.6, 
			WIDTH*0.15, HEIGHT*0.08, texto, function(){
				if(gameLoop == null)
					initComponents(WIDTH, HEIGHT);
			});
	//End RETRY
	cobra.draw();
	header.draw();
	controles.draw();
	tempoMoeda.comecar();
	gameLoop = setInterval("loop()", 1000/canvas.frames);
}
 function gcd(a, b) {
        return !b ? a : gcd(b, a % b);
    }

    function lcm(a, b) {
        return (a * b) / gcd(a, b);   
    }

function Botao(x, y, width, height, texto, onclickAction)
/*
	Representa os botões na tela do jogador
	para se mover quando n houver teclado
	para se mover
*/
{
	this.nome = "Botão";
	this.texto = texto;
	this.dimensao = {x: width, y: height};
	this.posicao = {x: x, y: y};
	this.onclickAction = onclickAction;
	this.tamText = 0.1;
	this.cor = "rgba(141, 143, 144, 0.1)";
	var myself = this;
	canvas.objeto.addEventListener("mousedown", function(ex){
			myself.click(ex);
	});
	this.draw = function()
	{
			ctx.beginPath();
				ctx.shadowBlur = 3;
				ctx.shadowColor = "lightgray";
				ctx.fillStyle = this.cor;
				ctx.fillRect(this.posicao.x, this.posicao.y, this.dimensao.x, this.dimensao.y);
				ctx.shadowBlur = 0;
				ctx.shadowColor = "blue";
				ctx.font = "20px Segoe UI Light";
				ctx.fillStyle = "white";
				var textX = (this.posicao.x)+(this.dimensao.x/2 - ctx.measureText(this.texto).width/2) ;// - (ctx.measureText(this.texto).width/2);
				ctx.fillText(this.texto, textX, this.posicao.y+(this.dimensao.y/1.6));
			ctx.closePath();
	};
	this.click = function(ex)
	{
		var mouseAtualmenteX = ex.pageX - $(ex.target).offset().left;	
		var mouseAtualmenteY = ex.pageY - $(ex.target).offset().top;
		if(mouseAtualmenteX > this.posicao.x && mouseAtualmenteX <
		 	parseInt(this.posicao.x + this.dimensao.x) && mouseAtualmenteY >
		 	this.posicao.y && mouseAtualmenteY < parseInt(this.posicao.y + 
			this.dimensao.y))
				this.onclickAction();
	}			
}
function Controles()
{
	this.w = currentValores.controles.dimensao.x*WIDTH;
	this.h = currentValores.controles.dimensao.y*WIDTH;
	this.up = new Botao(currentValores.controles.up.x*WIDTH, currentValores.controles.up.y*HEIGHT,
		this.w, this.h, "Up", function(){cobra.turn.choose(38)});
	this.down = new Botao(currentValores.controles.down.x*WIDTH, currentValores.controles.down.y*HEIGHT,
		this.w, this.h, "Do", function(){cobra.turn.choose(40)});
	this.left = new Botao(currentValores.controles.left.x*WIDTH, currentValores.controles.left.y*HEIGHT,
		this.w, this.h, "Le", function(){cobra.turn.choose(37)});
	this.right = new Botao(currentValores.controles.right.x*WIDTH, currentValores.controles.right.y*HEIGHT,
		this.w, this.h, "Ri", function(){cobra.turn.choose(39)});
	this.draw = function()
	{
		this.up.draw();
		this.down.draw();
		this.left.draw();
		this.right.draw();
	}
}
function tratarColisoes()
/*
	Classe herdada de um outro projeto;
	Se mostra muito eficaz em dectar colisões
*/
{
	this.algumaColisao = function () 
	{
		if(this.cobraColisaoBody())
			return true;
		else if(this.cobraColisaoCoin())
			return true;
		else if(this.limitesTelaX())
			return true;
		else if(this.limitesTelaX2())
			return true;
		else if(this.limitesTelaY())
			return true;
		else if(this.limitesTelaY2())
			return true;
	}
	this.cobraColisaoCoin = function ()
	{
		/*
			Gera uma nova moeda, deixa a cobra maior, reinicia o contador,
			atualiza quantas units a cobra vai perde se o tempo acabar
			e muda o movimento circular para outro caso as units sejam maior
			15
		*/
		if(this.colidiu(cobra, moeda, false, 0))
		{
			var p = new Partes();
			cobra.partes.push(p);
			moeda.generate();
			tempoMoeda.recomecar();
			if((cobra.partes.length) % 4 == 0)
			{
				cobra.perdeSeMorrer.att();
				moeda.changeMovement();
			}
			if(cobra.partes.length > 15)
			{
				moeda.trig.sinDivide = 20;
				moeda.trig.cosDivide = 25;
				moeda.amplitude = 50;
			}
			return true;
		}
		else
			return false;
	};
	this.cobraColisaoBody = function()
	/*
		Encerra o jogo
	*/
	{
		for (var i = 1; i < cobra.partes.length; i++) 
		{
			if(cobra.partes[i].enabled)
			{
				if(this.colidiu(cobra, cobra.partes[i], false, 0))
				{
					clearTimeout(gameLoop);
					gameLoop = null;
					return true;
				}
			}
		}
	};
	this.limitesTelaX = function()
	{
		if(cobra.posicao.x > canvas.width)
		{
			//cobra.posicao.x = 0;
			clearTimeout(gameLoop);
			gameLoop = null;
			return true;
		}
	}

	this.limitesTelaX2 = function()
	{
		if(cobra.posicao.x < 0)
		{
			//cobra.posicao.x = canvas.width - cobra.dimensao.x;
			clearTimeout(gameLoop);
			gameLoop = null;
			return true;
		}
	}

	this.limitesTelaY = function()
	{
		if(cobra.posicao.y > canvas.height)
		{
			//cobra.posicao.y = HEIGHT*currentValores.header.dimensao.y;
			clearTimeout(gameLoop);
			gameLoop = null;
			return true;
		}
	}

	this.limitesTelaY2 = function()
	{
		if(cobra.posicao.y < HEIGHT*currentValores.header.dimensao.y)
		{
			//cobra.posicao.y = HEIGHT - cobra.dimensao.y;
			clearTimeout(gameLoop);
			gameLoop = null;
			return true;
		}
	}
	this.colidiu = function (objeto2, objeto1, margemErro, valor)//Checar colisão com ou sem margem de erro
	{
		/*
			Colisão parece simples matamática basica, e é, o problema é quando
			para o computador tudo é um fodendoo ponto sem dimensão.
		*/
		var x1 = parseInt(objeto1.posicao.x);	
		var y1 = parseInt(objeto1.posicao.y);
		var w1 = parseInt(objeto1.dimensao.x);
		var h1 = parseInt(objeto1.dimensao.y);

		var x2 = parseInt(objeto2.posicao.x);	
		var y2 = parseInt(objeto2.posicao.y);
		var w2 = parseInt(objeto2.dimensao.x);
		var h2 = parseInt(objeto2.dimensao.y);
		if((x2 > x1 + w1 || y2 > y1 + h1) || (x2 + w2 < x1 || y2 + h2 < y1))
		{
			return false;
		}
		if(margemErro)
		{
			if(x1 < x2 && (x1 + w1*valor) < x2)
			{
				return false;
			}
			else
			{
				if((x2 + w2) < (x1 + w1*(1 - valor)))
				{
					return false;
				}
			}
		}
		return true;
	};
}
function gameOver()
{
	clearTimeout(tempoMoeda.loop);
	canvas.limparTela();
	ctx.fillStyle = "#101010";
	ctx.fillRect(0, 0, WIDTH, HEIGHT);
	ctx.font = "bold 32px Segoe UI";
	ctx.fillStyle = "white";
	var texto = "PONTOS: "+cobra.partes.length;
	ctx.fillText("GAME OVER", WIDTH*0.5 - ctx.measureText("GAME OVER").width/2, HEIGHT*0.5);
	ctx.font = "bold 25px Segoe UI";
	ctx.fillText(texto, WIDTH*0.5 - ctx.measureText(texto).width/2, HEIGHT*0.3);
	retry.draw();
	/*var texto2 = "RETRY";
	ctx.fillText(texto2, WIDTH*0.5 - ctx.measureText(texto2).width/2, HEIGHT*0.7);*/
}
function Canvas()
{
	this.objeto = document.getElementById("canvas");
	this.width = WIDTH;
	this.height = HEIGHT;
	this.frames = 8;//8
	this.keys = [];
	this.keys[39] = false;
	this.keys[37] = false;
	this.keys[38] = false;
	this.keys[40] = false;
	this.limparTela = function()
	{
		ctx.clearRect(0, 0, this.width, this.height);
	};
	this.eventos = 
	{
		movimentoSeta: function(e)
		{	
			if(!(canvas.keys[37] || canvas.keys[38] || canvas.keys[39] || canvas.keys[40]))
			{
				if([37, 38, 39, 40].indexOf(e.keyCode) != -1)
				{
					cobra.turn.choose(e.keyCode);
					canvas.keys[e.keyCode] = true;
				}
			}	
		}
	}
}