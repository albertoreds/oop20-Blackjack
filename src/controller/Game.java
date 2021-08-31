package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Card;
import model.Chip;
import model.State;
import utility.ActionHandler;
import view.View;;



public class Game {
	
	//int pickedcard;
//	private int playerNumberCard = 0;
//	private int dealerNumberCard = 0;
//	private List<Card> playerHand = new ArrayList<Card>();
//	private List<Card> dealerHand = new ArrayList<Card>();
//	private int valueplayerhand;
//	private int valuedealerhand;
//	public ActionHandler actionHandler = new ActionHandler(this);
	private int balance = 0;
	private int bet = 0;
	private int chipcannotchange;//0 = chip 20 1 = chip 50 2 = chip 100
	private DealerDraw dealerDraw = new DealerDraw();
	private PlayerDraw playerDraw = new PlayerDraw();
	private PlayerTurn playerTurn = new PlayerTurn();
	private DealerTurn dealerTurn = new DealerTurn();
	public ActionHandler actionHandler = new ActionHandler(this,dealerDraw,playerDraw,dealerTurn,playerTurn);
	private State currentState;
	private View view;
	
	
	public Game() {
		this.balance = 1000;
	}
	
	public void setView(View view) {
		this.view = view;
	}
	
	public void newGame() {
		this.dealerDraw.DrawCard();
		this.playerDraw.DrawCard();
		this.dealerDraw.DrawCard();
		this.playerDraw.DrawCard();
		
		
		if(this.playerDraw.getPlayerHand().size() == 2 && this.playerDraw.getPointPlayer() == 21) { //&& this.dealerDraw.getDealerHand().get(0).getValues().getV() != 10) {
			this.currentState = State.natural;
			this.balance += this.bet*2.5;
		}else if(this.playerDraw.getPointPlayer() < 22) {
			this.currentState = State.playerTurn;
		}

		this.UpdateView();
	}

	public void UpdateView() {		
		this.view.draw(this.dealerDraw.getPointDealer(), this.playerDraw.getPointPlayer(),
				this.playerDraw.getPlayerHand(), this.dealerDraw.getDealerHand(), this.currentState, this.balance, this.bet);
	}
	
	public State getState() {
		return this.currentState;
	}
	
	public void setState(State state) {
		this.currentState = state;
	}
	
	public void CheckResult() {
		if(this.dealerDraw.getPointDealer() > this.playerDraw.getPointPlayer() && this.dealerDraw.getPointDealer() < 22 || this.playerDraw.getPointPlayer() > 21) {
			if(this.checkBalance()) {
				this.setState(State.broke);
			}else {
				this.setState(State.lose);
			}
		}else if(this.dealerDraw.getPointDealer() < this.playerDraw.getPointPlayer()) {
			this.setState(State.win);
			this.balance += this.bet*2;
		}else if(this.dealerDraw.getPointDealer() == this.playerDraw.getPointPlayer() && this.playerDraw.getPointPlayer() < 21 && this.dealerDraw.getPointDealer() < 21) {
			this.setState(State.drow);
			this.balance += this.bet;
		}else if(this.dealerDraw.getPointDealer() > 21) {
			this.setState(State.win);
			this.balance += this.bet*2;
		}
		
		this.UpdateView();
	}
	
	
	public void setBet(Chip chip) {
		this.bet += Chip.getChipValue(chip);
		this.setState(State.bet);
		this.UpdateView();
	}
	
	public int getBet() {
		return this.bet;
	}
	
	public void setBalanceAfterBet() {
		this.balance -= this.bet;
		this.UpdateView();
	}
	
	public boolean checkBalance() {
		if(this.balance < 20) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean checkbet(Chip chip) {
//		if(this.balance - this.bet - Chip.getChipValue(Chip.twenty) <= 0
//				|| this.balance - this.bet - Chip.getChipValue(Chip.fifty) <= 0
//				|| this.balance - this.bet - Chip.getChipValue(Chip.hundred) <= 0) {
//
//			this.setState(State.nobet);
//			this.UpdateView();
//		}	
		boolean doit = true;
		
		switch (chip) {
		case twenty:
			if(this.balance - this.bet - Chip.getChipValue(Chip.twenty) < 0) {
				this.setState(State.nobet);
				this.UpdateView();
				this.setWhichCannotChangeChip(0);
				doit = false;
			}
			return doit;
		case fifty:
			if(this.balance - this.bet - Chip.getChipValue(Chip.fifty) < 0) {
				this.setState(State.nobet);
				this.UpdateView();
				this.setWhichCannotChangeChip(1);
				doit = false;
			}
			return doit;
		case hundred:
			if(this.balance - this.bet - Chip.getChipValue(Chip.hundred) < 0) {
				this.setState(State.nobet);
				this.UpdateView();
				this.setWhichCannotChangeChip(2);
				doit = false;
			}
			return doit;
		}
		return doit;
	}
	
	public void setWhichCannotChangeChip(int numberchipcannotchange) {
		this.chipcannotchange = numberchipcannotchange;
	}
	
	public int getWhichCannotChangeChip() {
		return this.chipcannotchange;
	}
	
	
	public void ResetAll() {
		this.playerDraw.ResetPlayer();
		this.dealerDraw.ResetDealer();
		this.bet = 0;
		this.setState(State.bet);
		this.UpdateView();
		//this.newGame();
	}
	
	public void playAgain() {
		this.playerDraw.ResetPlayer();
		this.dealerDraw.ResetDealer();
		this.balance = 1000;
		this.bet = 0;
		this.setState(State.bet);
		this.UpdateView();
	}
}
