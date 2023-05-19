package CustomCrafts;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public abstract class AbstractChargeable extends ItemStack{

    private int COST = 1;
    private int MAX_AMOUNT = 10;
    private int LORE_CHRAGE_COUNTER = 4;

    protected boolean canRecharge() {
        int charge = getCharges();
        charge = charge + 1;
        if (charge > MAX_AMOUNT) {
            return false;
        }
        return true;
    }

    protected boolean canActivate() {
        int charge = getCharges();
        charge = charge - 1;
        if (charge <= 0){
            return false;
        }
        return true;
    }

    protected int getCharges() {
        ItemMeta meta = this.getItemMeta();
        List<String> lore = this.getItemMeta().getLore();
        int charge = Integer.parseInt(lore.get(LORE_CHRAGE_COUNTER));
        return charge;
    }

    protected void setCharges(Integer count) {
        ItemMeta meta = this.getItemMeta();
        List<String> lore = this.getItemMeta().getLore();
        lore.set(LORE_CHRAGE_COUNTER, count.toString());
        meta.setLore(lore);
        this.setItemMeta(meta);
    }

    protected void recharge(){
        if(canRecharge()){
            int charges = getCharges();
            charges++;
            setCharges(charges);
        }
    }

    protected void consumeCharges(){
        if(canActivate()){
            int charges = getCharges();
            charges--;
            setCharges(charges);
        }
    }

    private void beforeActivate(){
        if(canActivate()){
            consumeCharges();
            activate();
        }
    }
    protected abstract void activate();
}