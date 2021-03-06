package gujc.dotter.fragment;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
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
    private Context context;
    private SparseBooleanArray selectedItems =new SparseBooleanArray();
    private int prePosition = -1;
    private int position1;

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
        ArrayList<Notice> notice = new ArrayList<>();


        public Adapter(Query query) {
            super(query);
            storageReference = FirebaseStorage.getInstance().getReference();
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();

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
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedItems.get(position1)) {
                        // ????????? Item??? ?????? ???
                        selectedItems.delete(position1);
                    } else {
                        // ????????? ???????????? Item??? ??????????????? ??????
                        selectedItems.delete(prePosition);
                        // ????????? Item??? position??? ??????
                        selectedItems.put(position1, true);
                    }
                    // ?????? ???????????? ????????? ??????
                    if (prePosition != -1) notifyItemChanged(prePosition);
                    notifyItemChanged(position1);
                    // ????????? position ??????
                    prePosition = position1;
                }
            });

        }
    }

    private class Holder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView content;
        public TextView timestamp;
        public int position;

        public Holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            timestamp = itemView.findViewById(R.id.timestamp);

            changeVisibility(selectedItems.get(position));

        }
        private void changeVisibility(final boolean isExpanded) {
            // height ?????? dp??? ???????????? ??????????????? ?????? ????????? ??????
            int dpValue = 150;
            float d = context.getResources().getDisplayMetrics().density;
            int height = (int) (dpValue * d);

            // ValueAnimator.ofInt(int... values)??? View??? ?????? ?????? ??????, ????????? int ??????
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
            // Animation??? ???????????? ??????, n/1000???
            va.setDuration(600);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // value??? height ???
                    int value = (int) animation.getAnimatedValue();
                    // imageView??? ?????? ??????
                    content.getLayoutParams().height = value;
                    content.requestLayout();
                    // imageView??? ????????? ?????????????????? ??????
                    content.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            });
            // Animation start
            va.start();
        }
    }

}
