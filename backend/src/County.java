
public class County {
    private String countyName;
    private int parentStateId;
    private int countyId;
    public County(String countyName, int parentStateId, int countyId){
        this.countyName = countyName;
        this.parentStateId = parentStateId;
        this.countyId = countyId;
    }

    public String getName(){
        return this.countyName;
    }

    public int getId(){
        return this.countyId;
    }

    public int getParentId(){
        return this.parentStateId;
    }

    public void setParentId(int parentId){
        this.parentStateId = parentId;
    }

    public String toString(){
        String str = this.countyName + "\nID : " + Integer.toString(this.countyId) + "\n Parent ID : " + Integer.toString(this.parentStateId);
        return str;
    }
}