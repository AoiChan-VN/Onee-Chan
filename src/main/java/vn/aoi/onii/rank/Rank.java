package vn.aoi.onii.rank;
import java.util.*;

public class Rank {
    private final String name;
    private final int max;
    private final String next;
    private final boolean doKiep;
    private final Map<Integer,Integer> map=new HashMap<>();
    private int flat;

    public Rank(String n,int m,String nx,boolean dk){name=n;max=m;next=nx;thienKiep=dk;}
    public void addLevel(int l,int e){map.put(l,e);}    
    public int getExp(int l){return map.isEmpty()?flat:map.getOrDefault(l,flat);}    
    public void setFlatExp(int f){flat=f;}    
    public int getMax(){return max;}    
    public String next(){return next;}    
    public boolean isThienKiep(){return thienKiep;} }
