package application;

public class Cards {
	//Kart verileri
	int id, card_id,check_flag;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCard_id() {
		return card_id;
	}
	public void setCard_id(int card_id) {
		this.card_id = card_id;
	}
	public int getCheck_flag() {
		return check_flag;
	}
	public void setCheck_flag(int check_flag) {
		this.check_flag = check_flag;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	String description;
	public Cards(int id,int card_id, String description,int check_flag) {
		this.id = id;
		this.card_id = card_id;
        this.description = description;
        this.check_flag=0;
    }
}
