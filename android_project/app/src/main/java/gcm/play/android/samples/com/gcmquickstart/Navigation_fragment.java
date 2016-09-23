package gcm.play.android.samples.com.gcmquickstart;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.RecursiveAction;


/**
 * A simple {@link Fragment} subclass.
 */
public class Navigation_fragment extends Fragment {
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private View containerView;
    private static String PREF_FILE_NAME="drawer";
    private static String KEY_USER_LEARNED_DRAWER="user_learned_drawer";
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    private RecyclerView recyclerList;
    private DashBoardRecyclerViewAdapter adapter;


    public Navigation_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer=Boolean.parseBoolean(readFromPreferences(getContext(),KEY_USER_LEARNED_DRAWER,"false"));
        if(savedInstanceState!=null)
            mFromSavedInstanceState=true;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_navigation_fragment, container, false);
        recyclerList=(RecyclerView)view.findViewById(R.id.recylerList);
        adapter=new DashBoardRecyclerViewAdapter(getContext());
        recyclerList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerList.setAdapter(adapter);
        recyclerList.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerList, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        }));

        return view;
    }


    public void setUp(DrawerLayout drawerLayout, final Toolbar toolbar,int fragmentId) {
        containerView=getActivity().findViewById(fragmentId);
        mDrawerLayout=drawerLayout;
        mDrawerToggle=new ActionBarDrawerToggle(getActivity(), mDrawerLayout,toolbar,R.string.drawerOpen, R.string.drawerClose){
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer)
                {
                    mUserLearnedDrawer=true;
                    saveToPreferences(getContext(),KEY_USER_LEARNED_DRAWER,String.valueOf(mUserLearnedDrawer));
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if(slideOffset<0.6)
                    toolbar.setAlpha(1-slideOffset);
            }
        };

        if(!mUserLearnedDrawer && !mFromSavedInstanceState)
        {
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }


    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue)
    {
        SharedPreferences preferences=context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }
    public static String readFromPreferences(Context context, String preferenceName, String defaultValue)
    {
        SharedPreferences preferences=context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return preferences.getString(preferenceName, defaultValue);

    }


    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        public RecyclerTouchListener(Context context, RecyclerView recyclerView, ClickListener clickListener)
        {

        }
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    interface ClickListener {
        public void onClick(View view, int position);
    }
}
