package talks.foazo.com.subhloansproject.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import talks.foazo.com.subhloansproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link wordCounterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link wordCounterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class wordCounterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private TextView wordCounterTextView;

    private OnFragmentInteractionListener mListener;

    public wordCounterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment wordCounterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static wordCounterFragment newInstance(String param1, String param2) {
        wordCounterFragment fragment = new wordCounterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_word_counter, container, false);

        wordCounterTextView = view.findViewById(R.id.wordCounterTextView);
//        Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
//        Matcher match= pt.matcher(mParam1);
//        while(match.find())
//        {
//            String s = match.group();
//            mParam1 = mParam1.replaceAll("\\"+s, "");
//        }

        String[] list = mParam1.replaceAll("[^a-zA-Z]+"," ").split("\\s+");

        ArrayList<String> wordRef = new ArrayList<String>();
        ArrayList<Integer> wordCount = new ArrayList<Integer>();

        for(int i = 0; i < list.length; i++){
            if(wordRef.contains(list[i])){
                int wordIndex = wordRef.indexOf(list[i]);
                wordCount.set(wordIndex, (wordCount.get(wordIndex) + 1));
            }else{
                wordRef.add(String.valueOf(list[i]));
                wordCount.add(1);
            }
        }

        String stringToDisplay = "";

        for(int n = 1; n < wordRef.size(); n++){
            stringToDisplay+= "\"" + wordRef.get(n) + "\" appears " + String.valueOf(wordCount.get(n)) + " times" + System.getProperty ("line.separator");
        }

        wordCounterTextView.setText(stringToDisplay);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
