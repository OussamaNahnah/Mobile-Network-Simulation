package sample;

import java.util.ArrayList;

public class STATION_DE_BASE {
    int id;
    int x;
    int y;
    ArrayList<NOEUD> listeMesNoeuds;

    public STATION_DE_BASE(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        listeMesNoeuds=new   ArrayList<NOEUD>();
    }



    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getId() {
        return id;
    }

  /*  public void aff() {
        for (int i = 0; i < listeMesNoeuds.size(); i++) {
            System.out.println("noeude " + listeMesNoeuds.get(i).getId() + " x: " + listeMesNoeuds.get(i).getX() + " y:" + listeMesNoeuds.get(i).getY()+"/ ");
        }
    }*/

    public ArrayList<NOEUD> getListeMesNoeuds() {
        return listeMesNoeuds;
    }

    public void setNoeuds(NOEUD noeud) {
       this.listeMesNoeuds.add(noeud);    }


}
