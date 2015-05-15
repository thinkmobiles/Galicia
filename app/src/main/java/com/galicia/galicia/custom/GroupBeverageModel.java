package com.galicia.galicia.custom;

import com.cristaliza.mvc.models.estrella.Item;

import java.util.List;

/**
 * Created by Michael on 14.05.2015.
 */
public final class GroupBeverageModel {
    public String title;
    public List<Item> beverageModels;

    public GroupBeverageModel(String title, List<Item> beverageModels) {
        this.title = title;
        this.beverageModels = beverageModels;
    }

//    public static ArrayList<BeverageModel> mockListData1(){
//        final ArrayList<BeverageModel> beverageModels = new ArrayList<>();
//        beverageModels.add(new BeverageModel(0, "http://pixabay.com/static/uploads/photo/2013/07/12/16/28/bordeaux-150955_640.png"));
//        beverageModels.add(new BeverageModel(1, "http://dostavka-alkogolya.com/images/pivo/corona-extra.png"));
//        beverageModels.add(new BeverageModel(2, "http://dostavka-alkogolya.com/images/pivo/corona-extra.png"));
//        beverageModels.add(new BeverageModel(3, "http://drink-beer.ru/beer_data/pictures/Khamovniki-Plzenskoe-bottle.jpg"));
//        beverageModels.add(new BeverageModel(4, "http://drink-beer.ru/beer_data/pictures/Khamovniki-Plzenskoe-bottle.jpg"));
//        beverageModels.add(new BeverageModel(5, "http://dostavka-alkogolya.com/images/pivo/corona-extra.png"));
//        return beverageModels;
//    }
//
//    public static ArrayList<BeverageModel> mockListData2(){
//        final ArrayList<BeverageModel> beverageModels = new ArrayList<>();
//        beverageModels.add(new BeverageModel(0, "http://pixabay.com/static/uploads/photo/2013/07/12/16/28/bordeaux-150955_640.png"));
//        beverageModels.add(new BeverageModel(1, "http://dostavka-alkogolya.com/images/pivo/corona-extra.png"));
//        beverageModels.add(new BeverageModel(2, "http://pixabay.com/static/uploads/photo/2013/07/12/16/28/bordeaux-150955_640.png"));
//        beverageModels.add(new BeverageModel(3, "http://dostavka-alkogolya.com/images/pivo/corona-extra.png"));
//        beverageModels.add(new BeverageModel(4, "http://pixabay.com/static/uploads/photo/2013/07/12/16/28/bordeaux-150955_640.png"));
//        beverageModels.add(new BeverageModel(5, "http://dostavka-alkogolya.com/images/pivo/corona-extra.png"));
//        beverageModels.add(new BeverageModel(6, "http://drink-beer.ru/beer_data/pictures/Khamovniki-Plzenskoe-bottle.jpg"));
//        beverageModels.add(new BeverageModel(7, "http://dostavka-alkogolya.com/images/pivo/corona-extra.png"));
//        beverageModels.add(new BeverageModel(8, "http://pixabay.com/static/uploads/photo/2013/07/12/16/28/bordeaux-150955_640.png"));
//        beverageModels.add(new BeverageModel(9, "http://dostavka-alkogolya.com/images/pivo/corona-extra.png"));
//        beverageModels.add(new BeverageModel(10,"http://pixabay.com/static/uploads/photo/2013/07/12/16/28/bordeaux-150955_640.png"));
//        beverageModels.add(new BeverageModel(11,"http://dostavka-alkogolya.com/images/pivo/corona-extra.png"));
//        return beverageModels;
//    }
//
//    public static ArrayList<BeverageModel> mockListData3(){
//        final ArrayList<BeverageModel> beverageModels = new ArrayList<>();
//        beverageModels.add(new BeverageModel(0, "http://dostavka-alkogolya.com/images/pivo/corona-extra.png"));
//        beverageModels.add(new BeverageModel(1, "http://pixabay.com/static/uploads/photo/2013/07/12/16/28/bordeaux-150955_640.png"));
//        beverageModels.add(new BeverageModel(2, "http://dostavka-alkogolya.com/images/pivo/corona-extra.png"));
//        beverageModels.add(new BeverageModel(3, "http://dostavka-alkogolya.com/images/pivo/corona-extra.png"));
//        beverageModels.add(new BeverageModel(4, "http://pixabay.com/static/uploads/photo/2013/07/12/16/28/bordeaux-150955_640.png"));
//        beverageModels.add(new BeverageModel(5, "http://dostavka-alkogolya.com/images/pivo/corona-extra.png"));
//        beverageModels.add(new BeverageModel(6, "http://pixabay.com/static/uploads/photo/2013/07/12/16/28/bordeaux-150955_640.png"));
//        beverageModels.add(new BeverageModel(7, "http://dostavka-alkogolya.com/images/pivo/corona-extra.png"));
//        beverageModels.add(new BeverageModel(8, "http://drink-beer.ru/beer_data/pictures/Khamovniki-Plzenskoe-bottle.jpg"));
//        beverageModels.add(new BeverageModel(9, "http://dostavka-alkogolya.com/images/pivo/corona-extra.png"));
//        beverageModels.add(new BeverageModel(10,"http://dostavka-alkogolya.com/images/pivo/corona-extra.png"));
//        beverageModels.add(new BeverageModel(11,"http://pixabay.com/static/uploads/photo/2013/07/12/16/28/bordeaux-150955_640.png"));
//        beverageModels.add(new BeverageModel(12,"http://dostavka-alkogolya.com/images/pivo/corona-extra.png"));
//        beverageModels.add(new BeverageModel(13,"http://drink-beer.ru/beer_data/pictures/Khamovniki-Plzenskoe-bottle.jpg"));
//        beverageModels.add(new BeverageModel(14,"http://drink-beer.ru/beer_data/pictures/Khamovniki-Plzenskoe-bottle.jpg"));
//        beverageModels.add(new BeverageModel(15,"http://drink-beer.ru/beer_data/pictures/Khamovniki-Plzenskoe-bottle.jpg"));
//        beverageModels.add(new BeverageModel(16,"http://drink-beer.ru/beer_data/pictures/Khamovniki-Plzenskoe-bottle.jpg"));
//        beverageModels.add(new BeverageModel(17,"http://dostavka-alkogolya.com/images/pivo/corona-extra.png"));
//        beverageModels.add(new BeverageModel(18,"http://dostavka-alkogolya.com/images/pivo/corona-extra.png"));
//        beverageModels.add(new BeverageModel(19,"http://dostavka-alkogolya.com/images/pivo/corona-extra.png"));
//        return beverageModels;
//    }
}
