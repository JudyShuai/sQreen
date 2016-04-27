package com.somoplay.screenshow.activity;

import android.os.Bundle;

import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.database.StoreDishDB;
import com.somoplay.screenshow.database.StoreMediaDB;
import com.somoplay.screenshow.model.StoreDish;
import com.somoplay.screenshow.model.StoreMedia;
import com.somoplay.screenshow.util.LYDateString;

import java.util.ArrayList;

/**
 * Created by Shaohua on 8/26/2015.
 */
public class DishDetailActivity extends CommonItemDetailActivity{
    private StoreDish storeDish;
    private StoreDishDB storeDishDB = new StoreDishDB(this);
    private StoreMediaDB storeMediaDB = new StoreMediaDB(this);
    public ArrayList<StoreMedia> detailPicsDish = new ArrayList<>();

    public int itemId;
    public int mediaType = Constants.DISH_EXTRA_PHOTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemId = getIntent().getIntExtra(Constants.DISH_ID, 0);
        storeDish = storeDishDB.getStoreDishById(itemId);
        detailPicsDish = storeMediaDB.getStoreMediaByMediaTypeAndElementId(4, itemId);

        setDetailPics(itemId);

        textName = storeDish.getName();
        price = String.valueOf(storeDish.getPrice());
        if(storeDish.getSalePercent() > 0){
            salePrice = String.valueOf(storeDish.getSalePercent() * storeDish.getPrice());
        }else{
            salePrice = "";
        }

        price = stringToDecimal(price);
        salePrice = stringToDecimal(salePrice);

        if(storeDish.getSaleExpire() == null){
            expirationDate = "2000-01-01 22:57:44.0";
        }else{
            expirationDate = LYDateString.dateToString(storeDish.getSaleExpire(), 3);
        }
        textContent = "";

        textContent = textContent + "\nMaterials: " + storeDish.getInfoOne() + "\nNutrition: "
                + storeDish.getInfoTwo()
                + "How do you know if your baby is ready for solid foods?  " +
                "There are many signs to look for that will indicate that your " +
                "little one may be ready to begin the journey into solid foods. Your " +
                "baby may be 3 months old or 4 months old when you start to feel she " +
                "may need “something more” than formula or breast milk.\n" +
                "Maybe she is beginning to awaken more often at night or eat\n" +
                "image: http://images.intellitxt.com/ast/adTypes/icon1.png\n" +
                " more often than “usual” and you wonder if introducing solid foods may " +
                "be what she needs.Please keep in mind that a growth spurt will occur " +
                "between 3-4 months of age. Your baby may begin to wake more frequently at " +
                "night for a feeding and/or may being to eat non-stop (cluster feed) as she " +
                "once did as a newborn.\n" +
                "Read more at http://wholesomebabyfood.momtastic.com/solids.htm#BgkWpokMg6QtfyTJ.99" +
                "Current recommendations indicate that breast milk or formula should be baby’s main " +
                "source of nutrition until at least 6 months of age.  While many pediatricians recommend " +
                "starting solid foods sometime between 4 and six months of age, the earlier introduction " +
                "of solid foods may have certain risk factors; consult your pediatrician. For example," +
                " the “Introduction of complementary feedings [solid foods] before 6 months of age g" +
                "enerally does not increase total caloric intake or rate of growth and only substitutes " +
                "foods that lack needed nutrients and the protective components of human milk (and formula)." +
                "Read more at http://wholesomebabyfood.momtastic.com/solids.htm#BgkWpokMg6QtfyTJ.99";

        fillTextIntoTextViews();

        //imagelocalUrls.add(Constants.DEVICE_PATH_DISH + "/" + storeDish.getMediaUrl());

        getExtraPics(mediaType, itemId, Constants.DEVICE_PATH_DISH);


        if(storeDish.getInfoOne().length()>0)
        {
            textContent = textContent + "Materials: " + storeDish.getInfoOne();
        }
        if(storeDish.getInfoTwo().length()>0)
        {
            textContent = textContent + "\n\nNutrition: " + storeDish.getInfoTwo();
        }
        if(storeDish.getInfoThree().length()>0)
        {
            textContent = textContent + "\n\nInfo: " + storeDish.getInfoThree();
        }

    }

}
