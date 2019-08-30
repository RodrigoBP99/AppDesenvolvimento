package br.com.rodrigo.meudiariopessoal.Adapter;

import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.rodrigo.meudiariopessoal.Activity.ContainerActivity;
import br.com.rodrigo.meudiariopessoal.DAO.AppDatabase;
import br.com.rodrigo.meudiariopessoal.Model.Confissao;
import br.com.rodrigo.meudiariopessoal.R;

public class ConfissaoAdapter extends RecyclerView.Adapter<ConfissaoAdapter.ConfissaoViewHolder> {
    private ArrayList<Confissao> confissaoArrayList;
    private Context context;

    public ConfissaoAdapter(ArrayList<Confissao> confissaoArrayList, Context context) {
        this.confissaoArrayList = confissaoArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ConfissaoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_confissao, viewGroup, false);
        return new ConfissaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConfissaoViewHolder confissaoViewHolder, int posicao) {
        final Confissao confissao = confissaoArrayList.get(posicao);

        confissaoViewHolder.textViewConfissao.setText(confissao.getTexto());
        confissaoViewHolder.textViewData.setText(confissao.getData());
        confissaoViewHolder.textViewHora.setText(confissao.getHora());

        confissaoViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(context);
                alerta.setTitle("Apagar").setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppDatabase appDatabase = Room.databaseBuilder(context, AppDatabase.class, "confissaoDataBase")
                                .allowMainThreadQueries()
                                .build();
                        appDatabase.confissaoDao().delete(confissao);
                    }
                }).setNegativeButton("Não", null).setMessage("Deseja excluir?").show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return confissaoArrayList.size();
    }

    public class ConfissaoViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewConfissao;
        private TextView textViewData;
        private TextView textViewHora;


        public ConfissaoViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewConfissao = itemView.findViewById(R.id.textViewConfissao);
            textViewData = itemView.findViewById(R.id.textViewData);
            textViewHora = itemView.findViewById(R.id.textViewHora);
        }
    }
}
