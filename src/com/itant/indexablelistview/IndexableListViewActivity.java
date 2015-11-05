package com.itant.indexablelistview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;

import com.itant.indexablelistview.utils.Hanzi2Pinyin;
import com.itant.indexablelistview.view.IndexableListView;

public class IndexableListViewActivity extends Activity {
	private ArrayList<String> mItems;
	private IndexableListView mListView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mItems = new ArrayList<String>();
        mItems.add("Diary of a Wimpy Kid 6: Cabin Fever");
        mItems.add("Steve Jobs");
        mItems.add("Inheritance (The Inheritance Cycle)");
        mItems.add("哈哈: A Novel");
        mItems.add("大 Hunger Games");
        mItems.add("Catching Fire (The Second Book of the Hunger Games)");
        mItems.add("Death Comes to Pemberley");
        mItems.add("Diary of a Wimpy Kid 6: Cabin Fever");
        mItems.add("Steve Jobs");
        mItems.add("11/22/63: A Novel");
        mItems.add("The Hunger Games");
        mItems.add("The LEGO Ideas Book");
        mItems.add("Explosive Eighteen: A Stephanie Plum Novel");
        mItems.add("Elder Scrolls V: Skyrim: Prima Official Game Guide");
        
        Collections.sort(mItems, new Comparator<String>() {

			@Override
			public int compare(String lhs, String rhs) {
				Hanzi2Pinyin hanzi2Pinyin = new Hanzi2Pinyin();
				// TODO Auto-generated method stub
				return hanzi2Pinyin.getStringPinYin(lhs.replaceAll(" ", "")).compareToIgnoreCase(hanzi2Pinyin.getStringPinYin(rhs.replaceAll(" ", "")));
			}
		});

        ContentAdapter adapter = new ContentAdapter(this,
                android.R.layout.simple_list_item_1, mItems);
        
        mListView = (IndexableListView) findViewById(R.id.listview);
        mListView.setAdapter(adapter);
        mListView.setFastScrollEnabled(true);
    }
    
    private class ContentAdapter extends ArrayAdapter<String> implements SectionIndexer {
    	
    	private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    	
		public ContentAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
		}

		@Override
		public int getPositionForSection(int section) {
			// section：所点击字母在右侧数组中的位置，从0开始。
			// getCount()：ListView的总条目数
			// 查找结束后的i：真正聚焦的字母在右侧数组中的位置（如果ListView中不存在当前点击的字母，则往前面聚焦。）
			// 查找结束后的j：所点击的字母在ListView的位置（如果ListView中不存在当前点击的字母，则往前面聚焦。）
			for (int i = section; i >= 0; i--) {
				for (int j = 0; j < getCount(); j++) {
					if (i == 0) {
						// 点击的是数字（或者点击的字母在LIstView项的首字母中没找到）
						for (int k = 0; k <= 9; k++) {
							//if (StringMatcher.match(String.valueOf(getItem(j).charAt(0)), String.valueOf(k)))
							if (TextUtils.equals(String.valueOf(getItem(j).charAt(0)), String.valueOf(k)))
								return j;
						}
					} else {
						// 用ListView每一项的首字母与点击的右侧字母进行对比
						if (TextUtils.equals(String.valueOf(getItem(j).charAt(0)), String.valueOf(mSections.charAt(i))))
							return j;
					}
				}
			}
			return 0;
		}

		@Override
		public int getSectionForPosition(int position) {
			return 0;
		}

		@Override
		public Object[] getSections() {
			String[] sections = new String[mSections.length()];
			for (int i = 0; i < mSections.length(); i++)
				sections[i] = String.valueOf(mSections.charAt(i));
			return sections;
		}
    }
}