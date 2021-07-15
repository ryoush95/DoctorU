package gujc.dotter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import gujc.dotter.R;
import gujc.dotter.bot.BotAdapter;
import gujc.dotter.common.FirestoreAdapter;
import gujc.dotter.fragment.ChartFragment;
import gujc.dotter.model.Board;
import gujc.dotter.model.Notice;

public class NoticeFragment extends Fragment {
    private RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirestoreAdapter firestoreAdapter;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private String timestamp1;
    private String title;
    private String content;

    public NoticeFragment(){    }

    @Override
    public void onStart() {
        super.onStart();
        if (firestoreAdapter != null) {
            firestoreAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (firestoreAdapter != null) {
            firestoreAdapter.stopListening();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        firestoreAdapter = new Adapter(FirebaseFirestore.getInstance()
                .collection("Notice").orderBy("timestamp"));
        LinearLayoutManager manager = new LinearLayoutManager(inflater.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(firestoreAdapter);
        System.out.println(firestoreAdapter);
        return view;
    }

    private class Adapter extends FirestoreAdapter<Holder> {
        private StorageReference storageReference;
        ArrayList<Notice> notice;

        public Adapter(Query query) {
            super(query);
            storageReference = FirebaseStorage.getInstance().getReference();
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_notice, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            final DocumentSnapshot documentSnapshot = getSnapshot(position);
            final Notice notice = documentSnapshot.toObject(Notice.class);


            holder.title.setText(notice.getTitle());
            timestamp1 = simpleDateFormat.format(notice.getTimestamp());
            holder.timestamp.setText(timestamp1);
            holder.content.setText(notice.getContent());


        }
    }

    private class Holder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView content;
        public TextView timestamp;

        public Holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            timestamp = itemView.findViewById(R.id.timestamp);
        }
    }
}
