package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Card;
import model.State;
import utility.ActionHandler;
import view.View;;



public class Game {
	
	//int pickedcard;
//	private int playerNumberCard = 0;
//	private int dealerNumberCard = 0;
	private List<Card> playerHand = new ArrayList<Card>();
	private List<Card> dealerHand = new ArrayList<Card>();
	private int valueplayerhand;
	private int valuedealerhand;
	public ActionHandler actionHandler = new ActionHandler(this);
	private DealerDraw dealerDraw = new DealerDraw();
	private PlayerDraw playerDraw = new PlayerDraw();
//	private PlayerTurn playerTurn = new PlayerTurn();
	private State currentState;
	private View view;
	
	
	public Game() {}
	
	public void setView(View view) {
		this.view = view;
	}
	
	public void newGame() {
		this.dealerDraw.DrawCard();
		this.playerDraw.DrawCard();
		this.dealerDraw.DrawCard();
		this.playerDraw.DrawCard();
		//this.playerTurn.PlayerDoTurn();
		
		this.dealerHand = this.dealerDraw.getDealerHand();
		this.playerHand = this.playerDraw.getPlayerHand();
		this.valuedealerhand = this.dealerDraw.getPointDealer();
		this.valueplayerhand = this.playerDraw.getPointPlayer();
		
		if(this.playerHand.size() == 2 && this.playerDraw.getPointPlayer() == 21) {
			this.currentState = State.natural;
		}else if(this.valueplayerhand < 22) {
			this.currentState = State.playerTurn;
		}else if(this.valueplayerhand > 21) {
			this.currentState = State.lose;
		}
		
		this.view.draw(this.valuedealerhand, this.valueplayerhand, playerHand, dealerHand, currentState);
		
		System.out.println("D:prima carta: "+ this.dealerHand.get(0).getSuit()+this.dealerHand.get(0).getValues()
				+"seconda carta: " + this.dealerHand.get(1).getSuit()+this.dealerHand.get(1).getValues());
		System.out.println("P:prima carta: "+ this.playerHand.get(0).getSuit()+this.playerHand.get(0).getValues()
				+"seconda carta: " + this.playerHand.get(1).getSuit()+this.playerHand.get(1).getValues());
	}
	//stati di gioco
	//geme finished
	//check result 
	//reset
	//player Natural

	//stati della view
	//menu...
	
	public List<Card> getPlayerHand(){
		return this.playerHand;
	}
	
	public List<Card> getDilerHand(){
		return this.dealerHand;
	}
	
	public State getState() {
		return this.currentState;
	}
}
