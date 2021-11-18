package org.headroyce.dp1;

public class Player {
    public int x;
    public int y;
    public String position;

    public Player (int x, int y, String position) {
        this.x = x;
        this.y = y;
        this.position = position;
    }
    public String toString(){
        String rtn = "(" + this.x +", "+this.y+")";
        return rtn;
    }

}
