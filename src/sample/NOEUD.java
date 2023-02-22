package sample;

public class NOEUD {
    int id ;
    int x;
    int y;
    int sb;
    public NOEUD(int id,int x,int y){
        this.id=id;
        this.x=x;
        this.y=y;
    }

    public int getSb() {
        return sb;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSb(int sb) {
        this.sb = sb;
    }

}
