var gameLoop;
var ctx, canvas, bola, barra, botao1, botao2, pauseButton, tratarColisoes, W, H, GAME_STATE;
var listaDeObstaculos = new Array();
var primeiraVez = true;
var cheat = true;
var opcoes =
{
	sons:{ligado: true, tipo: 'Google'},
	disposicaoObstaculo: 'Normal',
	botoes: false
}
function Canvas(width_canvas, height_canvas)
{
	/*
		Essa classe representa a tela de jogo
		e todas as funções relacionadas a ela
	*/
	this.objeto = document.getElementById("canvas");
	this.width = width_canvas;
	this.height = height_canvas;
	this.frames = 30;
	this.gravidade = 1;
	this.keys = [];
	this.limparTela = function()
	{
		ctx.clearRect(0, 0, this.width, this.height);
	};
	this.drawCoordenadas = function() 
	{
		ctx.beginPath();
			ctx.shadowBlur = 0;
			ctx.shadowColor = "";
			ctx.fillStyle = "white";
			ctx.font = "16px Arial";
			//ctx.fillText(exibirCoordenadas(barra),500, 400);
			//ctx.fillText(exibirCoordenadas(bola), 500, 450);
		ctx.closePath();
	};
	this.algumMovimento = function () 
	{
	    if (canvas.keys[39]) 
	    {
	       if (barra.velocidade.x < barra.velocidade.limite) 
	       {
	            barra.velocidade.x+=barra.velocidade.limite/3;
	        }
	    }
	    if (canvas.keys[37])
		{
		    if (barra.velocidade.x > -barra.velocidade.limite) 
		    {
		        barra.velocidade.x-=barra.velocidade.limite/3;
		    }
		}
       	barra.velocidade.x *= 0.8;
	};
	this.eventos = 
	{
		startx : 0,
    	dist : 0,
		movimentoMouse: function()//Caso o mouse seja utilizado para mover a barra 
		{
			//barra.posicao.x = event.pageX - $(event.target).offset().left - barra.dimensao.x/2;
		},
		keyboardEntrada: function()//Caso o teclado seja utilizado para mover a barra
		{
			if(event.keyCode == 39)
			{
				barra.posicao.x += barra.velocidade.x;
			}
			if(event.keyCode == 37)
			{
				barra.posicao.x -= barra.velocidade.x;
			}
		},
		mouseUp: function(ex)
		{
			var e = ex.changedTouches[0];
			var mouseAtualmenteX = e.pageX - $(ex.target).offset().left;
			var mouseAtualmenteY = e.pageY - $(ex.target).offset().top;
			if(opcoes.botoes && mouseAtualmenteX > botao1.posicao.x && mouseAtualmenteX <
			 	parseInt(botao1.posicao.x + botao1.dimensao.x) && mouseAtualmenteY > 
			 	botao1.posicao.y && mouseAtualmenteY < parseInt(botao1.posicao.y + botao1.dimensao.y))
			{
				canvas.keys[37] = false;
			}
			else if(opcoes.botoes && mouseAtualmenteX > botao2.posicao.x && mouseAtualmenteX <
			 		parseInt(botao2.posicao.x + botao2.dimensao.x) && mouseAtualmenteY > 
			 		botao2.posicao.y && mouseAtualmenteY < parseInt(botao2.posicao.y + botao2.dimensao.y))
			{
				canvas.keys[39] = false;
			}
		},
		clickada: function(ex)
		{
			var mouseAtualmenteX = ex.pageX - $(ex.target).offset().left;	
			var mouseAtualmenteY = ex.pageY - $(ex.target).offset().top;
			if(mouseAtualmenteX > pauseButton.posicao.x && mouseAtualmenteX <
			 	parseInt(pauseButton.posicao.x + pauseButton.dimensao.x) && mouseAtualmenteY >
			 	pauseButton.posicao.y && mouseAtualmenteY < parseInt(pauseButton.posicao.y + 
			 		pauseButton.dimensao.y))
			{
				if(gameLoop != null && GAME_STATE == 'RUN')
				{
					pauseButton.texto = "Continuar";
					pauseButton.draw();
					clearTimeout(gameLoop);
					gameLoop = null;
					GAME_STATE = 'PAUSE';
				}
				else if(GAME_STATE == 'PAUSE')
				{
					pauseButton.texto = "PAUSE";
					gameLoop = setInterval("loop()", 1000/canvas.frames);
					GAME_STATE = 'RUN';
				}
			}
			else if(gameLoop == null && GAME_STATE == 'LOSER')
			{
				if(mouseAtualmenteX > (W/2 - (W*0.7)/2) && mouseAtualmenteX <
			 		parseInt((W/2 - (W*0.7)/2) + W*0.7) && mouseAtualmenteY >
			 		H*0.25 && mouseAtualmenteY < parseInt(H*0.25 + 
			 		H*0.11))
			 	{
			 		bola.vidas = 3;
			 		gerarObstaculos();
					gameLoop = setInterval("loop()", 1000/canvas.frames);
					GAME_STATE = 'RUN';
			 	}
			}
			else if(gameLoop == null && GAME_STATE == 'WIN')
			{
				//W*0.03, H*0.4, W*0.3, H*0.08
				if(mouseAtualmenteX > W*0.03 && mouseAtualmenteX <
			 		parseInt(W*0.03 + W*0.3) && mouseAtualmenteY >
			 		H*0.4 && mouseAtualmenteY < parseInt(H*0.4 + 
			 		H*0.08))
				{
					window.open("https://www.youtube.com/watch?v=1-CMOMYdIlI","_blank")
				}
			}
		},
		mouseDown: function(ex)
		{
			var e = ex.changedTouches[0];
			var mouseAtualmenteX = e.pageX - $(ex.target).offset().left;
			var mouseAtualmenteY = e.pageY - $(ex.target).offset().top;
			this.startx = mouseAtualmenteX;
			if(!opcoes.botoes){barra.posicao.x = this.startx - barra.dimensao.x/2};
			if(opcoes.botoes && mouseAtualmenteX > botao1.posicao.x && mouseAtualmenteX <
			 	parseInt(botao1.posicao.x + botao1.dimensao.x) && mouseAtualmenteY >
			 	botao1.posicao.y && mouseAtualmenteY < parseInt(botao1.posicao.y + botao1.dimensao.y))
			{
				canvas.keys[39] = false;
				canvas.keys[37] = true;
			}
			else if(opcoes.botoes && mouseAtualmenteX > botao2.posicao.x && mouseAtualmenteX <
			 		parseInt(botao2.posicao.x + botao2.dimensao.x) && mouseAtualmenteY >
			 		botao2.posicao.y && mouseAtualmenteY < parseInt(botao2.posicao.y + botao2.dimensao.y))
			{
				canvas.keys[37] = false;
				canvas.keys[39] = true;
			}
		},
		touchMove: function(ex)
		{
			if(!opcoes.botoes){
				var e = ex.changedTouches[0];
				var mouseAtualmenteX = e.pageX - $(ex.target).offset().left;
				var dist = parseInt(mouseAtualmenteX - barra.dimensao.x/2);
				barra.posicao.x = dist;
			}
		}
	};
};
function Bola()
{
	this.nome = "Bola";
	this.velocidade = {x: 0, y: (H*0.023)/3.5, xC: (H*0.023)/1.6, yC: ((H*0.023)/1.6)};
	this.dimensao = {x: H*0.023, y: H*0.023};
	this.posicao = {x: W/2, y: H*0.4};
	this.raio = H*0.016;
	this.restituicao = -1;
	this.cor = "white";
	this.vidas = 4;
	this.colisoes = {emColisao: true, quantas: 0, vel: {y: (H*0.023)/3.5, x: (H*0.023)/2.5}};
	this.multKill = 0;
	this.draw = function() 
	{
		ctx.beginPath();
			//ctx.shadowBlur = 15;
			//ctx.shadowColor = "lightgray";
			ctx.fillStyle = this.cor;
			ctx.arc(this.posicao.x + this.dimensao.x*0.5, this.posicao.y + this.dimensao.x*0.5,
				this.raio, 0, Math.PI*2, true);
			ctx.fill();
	    	ctx.fillStyle = "green";
	    	//ctx.fillRect(bola.posicao.x, bola.posicao.y, bola.dimensao.x, bola.dimensao.y);
	    ctx.closePath();
	    ctx.beginPath();
	    	ctx.fillStyle = "white";
	    	for(var i = 1; i < this.vidas + 1; i++)
	    	{
	    		ctx.arc(i*(W*0.01)*2, 15, W*0.009, 0, Math.PI*2, true);
	    	}
	    	ctx.fill();
	    ctx.closePath();
	}
};
function Barra() 
{
	this.nome = "Barra";
	this.velocidade = {x: 0, y: 0, limite: (W*0.23)/6};
	this.dimensao = {x: W*0.23, y: H*0.02};
	this.posicao = {x: 0, y:H*0.75};
	this.draw = function () 
	{
		ctx.beginPath();
			ctx.shadowBlur = 0;
			ctx.shadowColor = "";
			bg = ctx.createLinearGradient(0,0,100,50);
			bg.addColorStop(0, "blue");
			bg.addColorStop(1, "royalblue");
			ctx.fillStyle = bg;
			ctx.fillRect(this.posicao.x, this.posicao.y, this.dimensao.x, this.dimensao.y);
			ctx.fillStyle = "lightgray";
			ctx.font = "16px Segoe UI Light";
			//ctx.fillText(canvas.keys[39]+" "+canvas.keys[37], canvas.width - 190, canvas.height - 10);
			/*ctx.fillRect(this.posicao.x, this.posicao.y, this.dimensao.x * 0.35, this.dimensao.y);
			ctx.fillStyle = "red";
			ctx.fillRect(this.posicao.x + this.dimensao.x*0.35, this.posicao.y, this.dimensao.x * 0.3,
				this.dimensao.y);
			ctx.fillStyle = "yellow";
			ctx.fillRect(this.posicao.x + this.dimensao.x *0.65, this.posicao.y, this.dimensao.x * 0.35,
				this.dimensao.y);*/
		ctx.closePath();
	}
};
function Obstaculo() 
{
	this.nome = "Obstaculo";
	this.posicao = {x: 42, y: 42};
	this.dimensao = {x: 42, y: 42};
	this.cor = "royalblue";
	this.draw = function()
	{
		ctx.beginPath();
			ctx.shadowBlur = 0;
			ctx.shadowColor = "";
			ctx.fillStyle = this.cor;
			ctx.fillRect(this.posicao.x, this.posicao.y, this.dimensao.x, this.dimensao.y);
		ctx.closePath();
	};
	this.getCalculatedDimension = function(num, total)
	{
        var div = Math.floor((total/num));
        if(div * num != total)
        {
            var diff = (total - (div*num));
            num+=(diff/div);
            return num;
        }
        else
        {
            return num;
        }
	}
};
function gerarObstaculos()
{
	listaDeObstaculos = [];
	if(opcoes.disposicaoObstaculo == "Normal")
	{
        var widthObstaculo = new Obstaculo().getCalculatedDimension(W*0.17, W*0.95);
        var heightObstaculo = new Obstaculo().getCalculatedDimension(H*0.025, H*0.95);
        //alert(widthObstaculo);
        var linhas = Math.floor(W/widthObstaculo);
        var colunas = Math.floor((H*0.2)/(heightObstaculo));
        //alert(linhas+" "+colunas);
        var espacamento = ((W*0.05)/(linhas+1));
        var espacamento2 = ((H*0.05)/(colunas+1));
		for(var i = 0; i < linhas; i++)
		{
			for(var j = 0; j < colunas; j++)
			{
				var r = Math.floor(Math.random() * 255);
				var g = Math.floor(Math.random() * 255);
				var b = Math.floor(Math.random() * 255);
				var obs = new Obstaculo();
                var x = (i * widthObstaculo)+(espacamento*(i+1));
                var y = (j * heightObstaculo)+(espacamento2*(j+1));
        		obs.posicao.x = x;
        		obs.posicao.y = y+H*0.1;
        		obs.dimensao.x = widthObstaculo;//120
        		obs.dimensao.y = heightObstaculo;
        		obs.cor = "rgb("+r+", "+g+", "+b+")";
        		listaDeObstaculos.push(obs);
        	}
        }
    }
    else
    {
        var heightObstaculo = new Obstaculo().getCalculatedDimension(H*0.025, H*0.95);
        var colunas = Math.floor((H*0.2)/(heightObstaculo));
        var espacamento = ((W*0.003));
    	for(var i = 0; i < colunas; i++)
    	{
    		var total = 0;
    		do
    		{
    			var r = Math.floor(Math.random() * 255);
    			var g = Math.floor(Math.random() * 255);
    			var b = Math.floor(Math.random() * 255);
    			var obs = new Obstaculo();
    			obs.posicao.x = total + espacamento;
        		obs.posicao.y = H*0.1+(i*heightObstaculo+(espacamento*(i+1)));//Chutes
        		var w = Math.floor(Math.random() * W*0.3 + 60);
        		w = (total + w + espacamento >= W - espacamento ? ((W - total) - espacamento) : w);
        		obs.dimensao.x = w - espacamento;
        		obs.dimensao.y = heightObstaculo;
        		obs.cor = "rgb("+r+", "+g+", "+b+")";
        		listaDeObstaculos.push(obs);
        		total = total + w + espacamento;
        	}while(total < (W - espacamento));
        }
    }
};
function exibirCoordenadas(obj) 
{
	var cordenadas = obj.nome.toUpperCase() + " = { posicao: {x: "+obj.posicao.x+", y: "+
	obj.posicao.y+"}, dimensao: {x: "+obj.dimensao.x+", y: "+obj.dimensao.y+"}}";
	return cordenadas;
};
function ColisaoComUnidades() 
{
	this.algumaColisao = function()
	{
		var alguma = false;
		if(this.barraColidiu())
		{
			//A Clara morreu!!! Quem você acha que vai ser a proxíma companion?
			alguma = true;//O que alguém vai fazer com isso?
		}
		if(this.bolaColisaoTerreno())
		{
			bola.colisoes.quantas += 1;
			new Sons().tocarParede();
			alguma = true;
		}
		if(this.bolaColisaoBarra())
		{
			new Sons().tocarBarra();
			bola.colisoes.quantas += 1;
			bola.multKill = 0;
			alguma = true;
		}
		if(this.bolaColisaoObstaculo())
		{
			bola.colisoes.quantas += 1;
			if(opcoes.sons.tipo == "Google")
			{
				new Sons().tocarObstaculo();
			}
			alguma = true;
		}
		if(bola.colisoes.emColisao && bola.colisoes.quantas > 5)
		{
			bola.velocidade.y = (bola.velocidade.y > 0 ? bola.velocidade.yC : -(bola.velocidade.yC));
			bola.velocidade.x = (bola.velocidade.x > 0 ? bola.velocidade.xC : -(bola.velocidade.xC));
			bola.colisoes.quantas = 0;
			bola.colisoes.emColisao = false;
		}
		return alguma;
	};
	this.colidiu = function (objeto2, objeto1, margemErro, valor)//Checar colisão com ou sem margem de erro
	{
		/*
			Colisão parece simples matamática basica, e é, o problema é quando
			quando seu algoritmo só consegue ver quadrados e você quer círculos
			então vc mostra um circulo pro usuário mas que internamente é
			tratado como um quadro e ambos compartilham o mesmo centro. 
			Ai vc vai codificar a frase acima e vê seu circulo adentrando
			25% dentro de tudo. Depois de 1 semana reescrevendo essa função
			vc descobre que na verdade o centro do círculo era o vertice
			superior esquerdo do quadrado '-'
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
			if(x1 < x2 && (x1 + w1*valor) < x2 || (y1 < y2 && (y1 + h1 * valor) < y2))
			{
				return false;
			}
			else
			{
				if((x2 + w2) < (x1 + w1*(1 - valor)) || (y2 + h2) < (y1 + h1 * (1 - valor)))
				{
					return false;
				}
			}
		}
		return true;
	};
	this.barraColidiu = function ()//Caso a barra colida com as paredes
	{
		if(barra.posicao.x >= canvas.width - barra.dimensao.x)
		{
			barra.posicao.x = canvas.width - barra.dimensao.x;
			return true;
		}
		if(barra.posicao.x < 0)
		{
			barra.posicao.x = 0;
			return true;
		}
		return false;
	};
	this.bolaColisaoTerreno = function ()
	{
		if(bola.posicao.y >= canvas.height)//Colisão chão
		{
			bola.velocidade.x = 0;
			bola.velocidade.y = bola.colisoes.vel.y;
			bola.posicao.y = canvas.height/2;
			bola.posicao.x = canvas.width/2;
			bola.colisoes.emColisao = true;
			bola.colisoes.quantas = 0;
			new Sons().tocarShutDown();
			bola.vidas -=1;
			if(bola.vidas <= 0)
			{	
				clearTimeout(gameLoop);
			}
			return false;
		}
	    if(bola.posicao.y < bola.raio)//Colisão teto
	    {
	    	bola.velocidade.y *= bola.restituicao;
	    	bola.posicao.y = bola.raio;
	    	return true;
	    }
	    if(bola.posicao.x > canvas.width - bola.raio)//Colisão lado direito
	    {
	    	bola.velocidade.x *= bola.restituicao;
	    	bola.posicao.x = canvas.width - bola.raio;
	    	return true;
	    }
	    if(bola.posicao.x < 0 + bola.raio)//Colisão lado esquerdo
	    {
	    	bola.velocidade.x *= bola.restituicao;
	    	bola.posicao.x = bola.raio;
	    	return true;
	    }
	    return false;
	};
	this.bolaColisaoBarra = function ()
	{
		if(this.colidiu(barra, bola, true, 0.75))
		{
			bola.velocidade.y *= bola.restituicao;
			bola.velocidade.x = this.parteDaBola(barra);
			bola.posicao.y = barra.posicao.y - bola.raio;
			multKill = 0;
			return true;
      		//bola.posicao.y = barra.posicao.y - bola.raio*2;
      	}
      	return false;
    };
	this.bolaColisaoObstaculo = function()
	{
	  	var houveColisao = false;
	  	for (var i = 0; i < listaDeObstaculos.length; i++) 
	  	{
	  		if(this.colidiu(listaDeObstaculos[i], bola, false, 1))
	  		{
	  			bola.velocidade.y *= bola.restituicao;
	  			bola.velocidade.x = this.parteDaBola(listaDeObstaculos[i]);
	  			listaDeObstaculos.splice(i, 1);
	  			houveColisao = true;
	  			if(opcoes.sons.tipo == "lol")
	  				switch(multKill)
	  			{
	  				case 0:
	  				new Sons().tocarSingleKill();
	  				break;
	  				case 1:
	  				new Sons().tocarDoubleKill();
	  				break;
	  				case 2:
	  				new Sons().tocarTripleKill();
	  				break;
	  				case 3:
	  				new Sons().tocarQuadraKill();
	  				break;
	  				case 4:
	  				new Sons().tocarPentaKill();
	  				multKill = -1;
	  				break;	
	  			}
	  			multKill++;
	  			break;
	  		}
	  	}
	  	return houveColisao;
	};
  	this.parteDaBola = function(barra)
  	{
  		var gambi;
  		if(bola.colisoes.emColisao)
  			gambi = bola.colisoes.vel.x;
  		else
  			gambi = bola.velocidade.xC;
  		if(bola.posicao.x < barra.posicao.x + (barra.dimensao.x * 0.35))
  			return gambi * (-1);
  		else if(bola.posicao.x + bola.dimensao.x < barra.posicao.x + (barra.dimensao.x * 0.7))
  			return 0;
  		return gambi;
  	};
};
function Sons() 
{
	this.colisaoBarra = document.getElementById("barra");
	this.colisaoObstaculo = document.getElementById("obstaculo");
	this.colisaoParede = document.getElementById("parede");
	this.shutDown = document.getElementById("shutDown");
	this.singleKill = document.getElementById("singleKill");
	this.doubleKill = document.getElementById("doubleKill");
	this.tripleKill = document.getElementById("tripleKill");
	this.quadraKill = document.getElementById("quadraKill");
	this.pentaKill = document.getElementById("pentaKill");
	this.ace = document.getElementById("ace");
	this.tocarAce = function() 
	{
		if(opcoes.sons.ligado)
		{
			this.ace.play();
		}
	}
	this.tocarBarra = function()
	{
		if(opcoes.sons.ligado)
		{
			this.colisaoBarra.play();
		}
	}
	this.tocarObstaculo = function()
	{
		if(opcoes.sons.ligado)
		{
			this.colisaoObstaculo.play();
		}
	}
	this.tocarParede = function()
	{
		if(opcoes.sons.ligado)
		{
			this.colisaoParede.play();
		}
	}
	this.tocarSingleKill = function() 
	{
		if(opcoes.sons.ligado)
		{
			if(cheat)
			{
				this.singleKill.currentTime = 0;
			}
			this.singleKill.play();
		}
	}
	this.tocarDoubleKill = function() 
	{
		if(opcoes.sons.ligado)
		{
			if(cheat)
			{
				this.singleKill.currentTime = 0;
			}
			this.doubleKill.play();
		}
	}
	this.tocarTripleKill = function() 
	{
		if(opcoes.sons.ligado)
		{
			if(cheat)
			{
				this.singleKill.currentTime = 0;
				this.doubleKill.currentTime = 0;
			}
			this.tripleKill.play();
		}
	}
	this.tocarQuadraKill = function() 
	{
		if(opcoes.sons.ligado)
		{
			if(cheat)
			{
				this.singleKill.currentTime = 0;
				this.doubleKill.currentTime = 0;
				this.tripleKill.currentTime = 0;
			}
			this.quadraKill.play();
		}
	}
	this.tocarPentaKill = function() 
	{
		if(opcoes.sons.ligado)
		{
			if(cheat)
			{
				this.singleKill.currentTime = 0;
				this.doubleKill.currentTime = 0;
				this.tripleKill.currentTime = 0;
				this.quadraKill.currentTime = 0;
			}
			this.pentaKill.play();
		}
	}
	this.tocarShutDown = function() 
	{
		if(opcoes.sons.ligado)
		{
			this.shutDown.play();
		}
	}
};
function initComponents(w, h) 
{
	W = w;
	H = h;
	canvas = new Canvas(w, h);
	ctx = canvas.objeto.getContext("2d");
	bola = new Bola();
	barra = new Barra();
	botao1 = new Botao("←", W*0.01, H*0.83);
	botao2 = new Botao("→", W*0.79, H*0.83);
	GAME_STATE = 'RUN';
	pauseButton = new Botao("PAUSE", W/2 - (W*0.3)/2, H*0.91);
	pauseButton.dimensao.x = W*0.3;
	pauseButton.dimensao.y = H*0.07;
	pauseButton.tamText = 0.05;
	pauseButton.cor = "rgba(224, 4, 14, 1)";
	canvas.keys[37] = false;
	canvas.keys[39] = false;
	tratarColisoes = new ColisaoComUnidades();
	gerarObstaculos();
	canvas.drawCoordenadas();
	window.addEventListener("keydown", function (e) {
	    	canvas.keys[e.keyCode] = true;
	});
	window.addEventListener("keyup", function (e) {
	    	canvas.keys[e.keyCode] = false;
	});
	canvas.objeto.addEventListener("touchstart", function (e) {
	    	canvas.eventos.mouseDown(e);
	});
	canvas.objeto.addEventListener("touchend", function (e) {
	    	canvas.eventos.mouseUp(e);
	});
	canvas.objeto.addEventListener("mousedown", function(e){
		//alert('11');
		canvas.eventos.clickada(e);
	});
	canvas.objeto.addEventListener("touchmove", function (e) {
	    	canvas.eventos.touchMove(e);
	});
	canvas.objeto.addEventListener("mousemove", canvas.eventos.movimentoMouse, false);
	for(var i = 0; i < listaDeObstaculos.length; i++)//Desenar obstaculos
	{
		listaDeObstaculos[i].draw();
	}
	bola.draw();
	barra.draw();
	botao1.draw();
	botao2.draw();
	pauseButton.draw();
	gameLoop = setInterval("loop()", 1000/canvas.frames);
}
function loop() 
{	
	tratarColisoes.algumaColisao();
	canvas.algumMovimento();
	barra.posicao.x += barra.velocidade.x;
	bola.posicao.y += bola.velocidade.y;
	bola.posicao.x += bola.velocidade.x;
	canvas.limparTela();
	canvas.drawCoordenadas();
	for(var i = 0; i < listaDeObstaculos.length; i++)//Desenar obstaculos
	{
		listaDeObstaculos[i].draw();
	}
	bola.draw();
	barra.draw();
	if (opcoes.botoes){
		botao1.draw();
		botao2.draw();	
	};
	pauseButton.draw();
	if(bola.vidas <= 0)
	{	
	   	clearTimeout(gameLoop);
		ctx.clearRect(0, 0, canvas.width, canvas.height);
		drawTARDIS();
		gameLoop = null;
		GAME_STATE = 'LOSER';
	}
	if(listaDeObstaculos.length == 0)
	{
	   	clearTimeout(gameLoop);
	   	gameLoop = null;
		ctx.clearRect(0, 0, canvas.width, canvas.height);
		new Sons().tocarAce();
		vencedor();
	}
}
function drawTARDIS()//Tédio...
{
	canvas.limparTela();
	ctx.beginPath();
	ctx.fillStyle = "green";
	ctx.fillRect(W/2 - (W*0.7)/2, H*0.25, W*0.7, H*0.11);
	ctx.font = W*0.07+"px Segoe UI Light";
	ctx.fillStyle = "white";
	ctx.shadowBlur = 3;
	ctx.shadowColor = "white";
	ctx.fillText("RETRY", W*0.4, H*0.33);
	ctx.font = W*0.1+"px Segoe UI Light";
	ctx.fillStyle = "gold";
	ctx.shadowBlur = 3;
	ctx.shadowColor = "white";
	ctx.fillText("GAME OVER", canvas.width/4.2, canvas.height/5);
	ctx.closePath();
}
function Botao(texto, lx, ly)
{
	this.nome = "Botão";
	this.texto = texto;
	this.dimensao = {x: W*0.2, y: H*0.15};
	this.posicao = {x: lx, y: ly};
	this.tamText = 0.1;
	this.cor = "rgba(141, 143, 144, 0.1)";
	this.draw = function()
	{
			ctx.beginPath();
				ctx.shadowBlur = 3;
				ctx.shadowColor = "lightgray";
				ctx.fillStyle = this.cor;
				ctx.fillRect(this.posicao.x, this.posicao.y, this.dimensao.x, this.dimensao.y);
				ctx.shadowBlur = 0;
				ctx.shadowColor = "blue";
				var cc = this.posicao.x+(this.dimensao.x/3.7);
				if(this.texto != "PAUSE")
					cc = this.posicao.x+(this.dimensao.x/6.5);
				ctx.font = W*this.tamText+"px Segoe UI Light";
				ctx.fillStyle = "white";
				ctx.fillText(this.texto, cc, this.posicao.y+(this.dimensao.y/1.3));
			ctx.closePath();
	};
}
function vencedor()
{
	GAME_STATE = 'WIN';
	alert("Você acaba de ser convidado para ver o maravilhoso\nMr. Kite");
	canvas.limparTela();
	ctx.beginPath();
		var img = document.getElementById("kite2");
		//img.src = 'sgt.png';
		ctx.drawImage(img, W*0.105, H*0.49, W*0.8, H*0.5);
		ctx.fillStyle = "white";
		ctx.font = "20px Segoe UI Light";
		ctx.fillText("The celebrated Mr. K ", W*0.03, H*0.075);
		ctx.fillText("Performs his feat on Saturday", W*0.03, H*0.135);
		ctx.fillText("At Bishop's Gate.", W*0.03, H*0.195);
		ctx.fillText("The Hendersons will dance and sing", W*0.03, H*0.255);
		ctx.fillText("As Mr. Kite flies through the ring.", W*0.03, H*0.315);
		ctx.fillStyle = "yellow";
		ctx.fillText(" -'Don't be late!'", W*0.03, H*0.375);
		ctx.fillStyle = "rgb(31, 29, 29)";
		ctx.fillRect(W*0.03, H*0.4, W*0.3, H*0.08);
		ctx.fillStyle = "white";
		ctx.fillText("PLAY", W*0.105, H*0.46);
	ctx.closePath();
}