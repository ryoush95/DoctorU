package gujc.dotter.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gujc.dotter.CustomDialog;
import gujc.dotter.R;
import gujc.dotter.bot.BotAdapter;
import gujc.dotter.model.Board;
import gujc.dotter.model.Chatbot;
import gujc.dotter.model.UserModel;

public class BotFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Chatbot> arrayList = new ArrayList<>();
    private BotAdapter botAdapter;
    private UserModel user;
    private Board board;
    private String fuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private String mcurrent = "nmtest";
    private String ucurrent = "";
    private String bcurrent = "";
    private List<String> arrayboard = new ArrayList<String>();
    private String doctor = "";
    private String doctorid = "";
    private String hospital = "";
    private String title = "";
    private String phoneNum = "";
    public String roomid;
    private FirebaseFirestore firebase = FirebaseFirestore.getInstance();
    private boolean request;
    ProgressDialog pd1;

    public BotFragment() {
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bot, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList = new ArrayList<>();
        botAdapter = new BotAdapter(arrayList);
        recyclerView.setAdapter(botAdapter);

        final Button button1 = (Button) view.findViewById(R.id.button1);
        final Button button2 = (Button) view.findViewById(R.id.button2);
        final Button button3 = (Button) view.findViewById(R.id.button3);
        final Button button4 = (Button) view.findViewById(R.id.button4);

        DocumentReference ref = FirebaseFirestore.getInstance().collection("users").document(fuser);
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(UserModel.class);
                Chatbot chatbot = new Chatbot("bot", "???????????????" + user.getUsernm() + "?????? ??????????");
                arrayList.add(chatbot);
                botAdapter.notifyDataSetChanged();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chatbot chatbot = new Chatbot(fuser, mcurrent);
                switch (chatbot.getCurrent()) {
                    case "nmtest": {
                        mcurrent = "nmyes";
                        ucurrent = "???";
                        bcurrent = "????????? ????????????????";
                        arrayboard.add("??????");
                        button1.setText("??????");
                        button2.setText("??????");
                        break;
                    }
                    case "nmyes": {
                        mcurrent = "dnl";
                        ucurrent = "??????";
                        bcurrent = "??? ????????? ???????????????1";
                        arrayboard.add(ucurrent);
                        button1.setText("??????");
                        button2.setText("??????");
                        button3.setText("???");
                        button3.setVisibility(View.VISIBLE);
                        break;
                    }
                    case "dnl": {
                        mcurrent = "head";
                        ucurrent = "??????";
                        bcurrent = "??? ????????? ???????????????2";
                        arrayboard.add(ucurrent);
                        button1.setText("??????");
                        button2.setText("?????? ???");
                        button3.setVisibility(View.GONE);
                        break;
                    }
                    case "head": {
                        mcurrent = "face";
                        ucurrent = "??????";
                        bcurrent = "??? ????????? ???????????????3";
                        arrayboard.add(ucurrent);
                        button1.setText("??????");
                        button2.setText("???????????????");
                        button3.setText("??????");
                        button3.setVisibility(View.VISIBLE);
                        break;
                    }
                    case "face": {
                        mcurrent = "card";
                        ucurrent = "??????";
                        bcurrent = "??????";
                        arrayboard.add(ucurrent);
                        button1.setText("??????");
                        button2.setVisibility(View.GONE);
                        button3.setVisibility(View.GONE);
                        break;
                    }

                    //??????2-1
                    case "nmno": {
                        mcurrent = "addman";
                        ucurrent = "??????";
                        bcurrent = "??????";
                        arrayboard.add(ucurrent);
                        button1.setText("?????? ??????");
                        button2.setVisibility(View.GONE);
                        button3.setVisibility(View.GONE);
                        button4.setVisibility(View.GONE);
                        break;
                    }
                    case "dkfo": {
                        mcurrent = "leg";
                        ucurrent = "??????";
                        bcurrent = "??? ????????? ???????????????2";
                        arrayboard.add(ucurrent);
                        button1.setText("?????????");
                        button2.setText("???");
                        break;
                    }
                    case "leg": {
                        mcurrent = "card";
                        ucurrent = "?????????";
                        bcurrent = "??????";
                        arrayboard.add(ucurrent);
                        button1.setText("??????");
                        button2.setVisibility(View.GONE);
                        button3.setVisibility(View.GONE);
                        button4.setVisibility(View.GONE);
                        break;
                    }
                    case "ear": {
                        mcurrent = "card";
                        ucurrent = "???";
                        bcurrent = "??????";
                        arrayboard.add(ucurrent);
                        button1.setText("??????");
                        button2.setVisibility(View.GONE);
                        button3.setVisibility(View.GONE);
                        button4.setVisibility(View.GONE);
                        break;
                    }

                    //??????3-1
                    case "stomach": {
                        mcurrent = "card";
                        ucurrent = "??????";
                        bcurrent = "??????";
                        arrayboard.add(ucurrent);
                        button1.setText("??????");
                        button2.setVisibility(View.GONE);
                        button3.setVisibility(View.GONE);
                        break;
                    }
                    //??????
                    case "card": {
                        mcurrent = "cyes";
                        ucurrent = "";
                        bcurrent = "???????????? ????????????????";
                        button1.setText("yes");
                        button2.setText("no");
                        button2.setVisibility(View.VISIBLE);
                        break;
                    }
                    case "cyes": {
                        ucurrent = "";
                        bcurrent = "??????????????? ???????????????";
                        mcurrent = "match";
                        button1.setText("?????? ????????????");
                        button2.setVisibility(View.GONE);
                        button3.setVisibility(View.GONE);
                        button4.setVisibility(View.GONE);

                        break;
                    }
                    case "match": {
                        button1.setVisibility(View.GONE);
                        roomid = firebase.collection("Board").document().getId();
                        CreateBoard(firebase.collection("Board").document(roomid));
                        String strboard = String.valueOf(arrayboard);
                        Map<String, Object> boardcur = new HashMap<>();
                        boardcur.put("title", strboard);
                        boardcur.put("id", fuser);
                        boardcur.put("timestamp", new Timestamp(new Date()));
                        boardcur.put("name", user.getUsernm() + " ???");
                        boardcur.put("match", false);
                        boardcur.put("request", false);
                        boardcur.put("doctor", "none");
                        boardcur.put("hospital", "none");
                        boardcur.put("doctorid", "none");
                        boardcur.put("status", 1);
                        boardcur.put("phone", user.getPhone());
                        boardcur.put("note","");

                        firebase.collection("Board").document(roomid).set(boardcur)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });
                        pd1 = ProgressDialog.show(getContext(), "", "?????? ???");
                        toDialog();

                        break;
                    }
                    default: {
                        break;
                    }
                }
                chatbot = new Chatbot(fuser, ucurrent);
                Chatbot chatbot1 = new Chatbot("bot", bcurrent);
                if (ucurrent.equals("")) {
                    arrayList.add(chatbot1);
                } else if (bcurrent.equals("")) {
                    arrayList.add(chatbot);
                } else {
                    arrayList.add(chatbot);
                    arrayList.add(chatbot1);
                }
                botAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(arrayList.size() - 1);


            }
        });

        //??????2
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chatbot chatbot = new Chatbot(fuser, mcurrent);
                switch (chatbot.getCurrent()) {
                    case "nmtest": {
                        mcurrent = "nmno";
                        ucurrent = "?????????";
                        bcurrent = "????????? ?????? ??????????";
                        arrayboard.add("??????");
                        button1.setText("??????");
                        button2.setText("?????????");
                        break;
                    }
                    case "nmno": {
                        mcurrent = "addman";
                        ucurrent = "??????";
                        bcurrent = "";
                        arrayboard.add(ucurrent);
                        button1.setText("??????");
                        button2.setVisibility(View.GONE);
                        button3.setVisibility(View.GONE);
                        button4.setVisibility(View.GONE);
                        break;
                    }
                    case "nmyes": {
                        mcurrent = "dkfo";
                        ucurrent = "??????";
                        bcurrent = "??? ????????? ???????????????1";
                        arrayboard.add(ucurrent);
                        button1.setText("??????");
                        button2.setText("???");
                        break;
                    }
                    case "dkfo": {
                        mcurrent = "card";
                        ucurrent = "???";
                        bcurrent = "??????";
                        arrayboard.add(ucurrent);
                        button1.setText("??????");
                        button2.setVisibility(View.GONE);
                        button3.setVisibility(View.GONE);
                        button4.setVisibility(View.GONE);
                        break;
                    }
                    case "dnl": {
                        mcurrent = "card";
                        ucurrent = "??????";
                        bcurrent = "??????";
                        arrayboard.add(ucurrent);
                        button1.setText("??????");
                        button2.setVisibility(View.GONE);
                        button3.setVisibility(View.GONE);
                        button4.setVisibility(View.GONE);
                        break;
                    }
                    case "head": {
                        mcurrent = "card";
                        ucurrent = "?????? ???";
                        bcurrent = "??????";
                        arrayboard.add(ucurrent);
                        button1.setText("??????");
                        button2.setVisibility(View.GONE);
                        button3.setVisibility(View.GONE);
                        break;
                    }
                    case "face": {
                        mcurrent = "card";
                        ucurrent = "???????????????";
                        bcurrent = "??????";
                        arrayboard.add(ucurrent);
                        button1.setText("??????");
                        button2.setVisibility(View.GONE);
                        button3.setVisibility(View.GONE);
                        break;
                    }
                    case "stomach": {
                        mcurrent = "card";
                        ucurrent = "?????????";
                        bcurrent = "??????";
                        arrayboard.add(ucurrent);
                        button1.setText("??????");
                        button2.setVisibility(View.GONE);
                        button3.setVisibility(View.GONE);
                        break;
                    }
                    case "card": {
                        ucurrent = "cno";
                        bcurrent = "????????????";
                        mcurrent = "????????????";
                        button1.setText("??????????????????");
                        button2.setVisibility(View.GONE);
                        button3.setVisibility(View.GONE);
                        button4.setVisibility(View.GONE);

                        break;
                    }
                    default: {
                        break;
                    }
                }
                chatbot = new Chatbot(fuser, ucurrent);
                Chatbot chatbot1 = new Chatbot("bot", bcurrent);
                if (ucurrent.equals("")) {
                    arrayList.add(chatbot1);
                } else if (bcurrent.equals("")) {
                    arrayList.add(chatbot);
                } else {
                    arrayList.add(chatbot);
                    arrayList.add(chatbot1);
                }
                botAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(arrayList.size() - 1);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chatbot chatbot = new Chatbot(fuser, mcurrent);
                switch (chatbot.getCurrent()) {
                    case "dnl": {
                        mcurrent = "stomach";
                        ucurrent = "???";
                        bcurrent = "??? ????????? ???????????????2";
                        arrayboard.add(ucurrent);
                        button1.setText("??????");
                        button2.setText("?????????");
                        button3.setVisibility(View.GONE);
                        break;
                    }
                    case "face": {
                        mcurrent = "card";
                        ucurrent = "??????";
                        bcurrent = "??????";
                        arrayboard.add(ucurrent);
                        button1.setText("??????");
                        button2.setVisibility(View.GONE);
                        button3.setVisibility(View.GONE);
                        break;
                    }

                    default: {
                        break;
                    }
                }
                chatbot = new Chatbot(fuser, ucurrent);
                Chatbot chatbot1 = new Chatbot("bot", bcurrent);
                if (ucurrent.equals("")) {
                    arrayList.add(chatbot1);
                } else if (bcurrent.equals("")) {
                    arrayList.add(chatbot);
                } else {
                    arrayList.add(chatbot);
                    arrayList.add(chatbot1);
                }
                botAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(arrayList.size() - 1);
            }
        });

        return view;

    }

    public void CreateBoard(final DocumentReference room) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", null);

        room.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
    }

    private void toDialog() {
        final DocumentReference docRef = firebase.collection("Board").document(roomid);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                board = snapshot.toObject(Board.class);

                doctor = board.getDoctor();
                hospital = board.getHospital();
                request = board.isRequest();
                doctorid = board.getDoctorid();
                title = board.getTitle();
                phoneNum = board.getPhone();

                if (request) {
                    pd1.dismiss();
                    CustomDialog customDialog = new CustomDialog(getContext());
                    customDialog.callFunction(roomid, doctor, hospital, doctorid, title,phoneNum);
                }
            }
        });
    }

}