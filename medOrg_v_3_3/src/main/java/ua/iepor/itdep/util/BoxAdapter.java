package ua.iepor.itdep.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.medorg.R;

import java.util.ArrayList;
/*import android.widget.CompoundButton.OnCheckedChangeListener;*/

public class BoxAdapter extends BaseAdapter {
  Context ctx;
  LayoutInflater lInflater;
  ArrayList<Product> objects;
  String[] quest;
  String[] ttl;

  public BoxAdapter(Context context, String[] quest_in, String[] titles, ArrayList<Product> products) {
    ctx = context;
    objects = products;
    quest = quest_in;
    ttl=titles;
    lInflater = (LayoutInflater) ctx
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  // кол-во элементов
  @Override
  public int getCount() {
    return objects.size();
  }

  // элемент по позиции
  @Override
  public Object getItem(int position) {
    return objects.get(position);
  }

  // id по позиции
  @Override
  public long getItemId(int position) {
    return position;
  }

  // пункт списка
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    // используем созданные, но не используемые view
    View view = convertView;
    if (view == null) {
      view = lInflater.inflate(R.layout.item, parent, false);
    }

    Product p = getProduct(position);

    // заполняем View в пункте списка данными из товаров: наименование, цена
    // и картинка
    ((TextView) view.findViewById(R.id.tvDescr)).setText(p.name);
    ((TextView) view.findViewById(R.id.tvPrice)).setText(p.question);
    /*((TextView) view.findViewById(R.id.tvPrice)).setText(p.price + "");
    ((ImageView) view.findViewById(R.id.ivImage)).setImageResource(p.image);*/

    /*CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbBox);
    // присваиваем чекбоксу обработчик
    cbBuy.setOnCheckedChangeListener(myCheckChangList);
    // пишем позицию
    cbBuy.setTag(position);
    // заполняем данными из товаров: в корзине или нет
    cbBuy.setChecked(p.box);*/
   
    
    
    RadioGroup radGrp = (RadioGroup) view.findViewById(R.id.radioGroup2);
    int checkedRadioButtonID = radGrp.getCheckedRadioButtonId();
    radGrp.setTag(position);
    
    radGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      public void onCheckedChanged(RadioGroup arg0, int id) {
    	  int k1=0;
        switch (id) {
        case -1:
          Log.v("LogStr", "Choices cleared!");
          break;
        case R.id.radio11:
          k1=1;
          break;
        case R.id.radio12:
        	k1=2;
          break;
        case R.id.radio13:
        	k1=3;
          break;
        case R.id.radio14:
            k1=4;
            break;
        
        default:
        	k1=0;
          break;
        }
    	  getProduct((Integer) arg0.getTag()).number = k1;
    	  Log.v("LogStr", "Id = "+arg0.getTag()+" k1= "+k1);
      }
    });
    
    switch (p.number){
    case 1:
    	((RadioButton) view.findViewById(R.id.radio11)).setChecked(true);
    	((RadioButton) view.findViewById(R.id.radio12)).setChecked(false);
    	((RadioButton) view.findViewById(R.id.radio13)).setChecked(false);
    	((RadioButton) view.findViewById(R.id.radio14)).setChecked(false);
    	break;
    case 2:
    	((RadioButton) view.findViewById(R.id.radio11)).setChecked(false);
    	((RadioButton) view.findViewById(R.id.radio12)).setChecked(true);
    	((RadioButton) view.findViewById(R.id.radio13)).setChecked(false);
    	((RadioButton) view.findViewById(R.id.radio14)).setChecked(false);
    	break;
    case 3:
    	((RadioButton) view.findViewById(R.id.radio11)).setChecked(false);
    	((RadioButton) view.findViewById(R.id.radio12)).setChecked(false);
    	((RadioButton) view.findViewById(R.id.radio13)).setChecked(true);
    	((RadioButton) view.findViewById(R.id.radio14)).setChecked(false);
    	break;
    case 4:
    	((RadioButton) view.findViewById(R.id.radio11)).setChecked(false);
    	((RadioButton) view.findViewById(R.id.radio12)).setChecked(false);
    	((RadioButton) view.findViewById(R.id.radio13)).setChecked(false);
    	((RadioButton) view.findViewById(R.id.radio14)).setChecked(true);
    	break;
    }
    /*((RadioButton) view.findViewById(R.id.radio11)).setChecked(false);
	((RadioButton) view.findViewById(R.id.radio12)).setChecked(false);
	((RadioButton) view.findViewById(R.id.radio13)).setChecked(false);
	((RadioButton) view.findViewById(R.id.radio14)).setChecked(false);*/
    return view;
  }

  // товар по позиции
  Product getProduct(int position) {
    return ((Product) getItem(position));
  }

  // содержимое корзины
  public ArrayList<Product> getBox() {
    ArrayList<Product> box = new ArrayList<Product>();
    for (Product p : objects) {
      /*// если в корзине
      if (p.box)*/
        box.add(p);
    }
    return objects;
  }

  /*// обработчик для чекбоксов
  OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
    public void onCheckedChanged(CompoundButton buttonView,
        boolean isChecked) {
      // меняем данные товара (в корзине или нет)
      getProduct((Integer) buttonView.getTag()).box = isChecked;
    }
  };*/
  
  
}
