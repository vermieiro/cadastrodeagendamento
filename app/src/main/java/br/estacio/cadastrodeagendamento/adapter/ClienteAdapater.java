package br.estacio.cadastrodeagendamento.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.estacio.cadastrodeagendamento.ImageCircle;
import br.estacio.cadastrodeagendamento.R;
import br.estacio.cadastrodeagendamento.model.Cliente;

/**
 * Created by aluno on 22/05/17.
 */

public class ClienteAdapater extends BaseAdapter {

    private Activity activity;
    private List<Cliente> list;

    private TextView txtNomeCliente, TxtFoneCliente;
    private ImageView foto;

    public ClienteAdapater(Activity activity, List<Cliente> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Cliente getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layout = activity.getLayoutInflater().inflate(R.layout.cliente, parent, false);

        foto = (ImageView) layout.findViewById(R.id.itemFoto);
        txtNomeCliente = (TextView) layout.findViewById(R.id.txtNomeCliente);
        TxtFoneCliente = (TextView) layout.findViewById(R.id.txtFoneCliente);


        Cliente cliente = getItem(position);
        txtNomeCliente.setText(cliente.getNome());
        TxtFoneCliente.setText(cliente.getFone());


        Bitmap bm;
        if (cliente.getCaminhoFoto() != null) {
            bm = BitmapFactory.decodeFile(cliente.getCaminhoFoto());
        }
        else {
            bm = BitmapFactory.decodeResource(activity.getResources(),
                    R.mipmap.ic_no_image);
        }
        foto.setImageBitmap(ImageCircle.crop(bm));
        return layout;
    }

}
