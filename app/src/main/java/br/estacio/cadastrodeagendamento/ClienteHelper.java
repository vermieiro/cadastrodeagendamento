package br.estacio.cadastrodeagendamento;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.AdapterView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.estacio.cadastrodeagendamento.dao.ClienteDAO;
import br.estacio.cadastrodeagendamento.model.Servico;
import br.estacio.cadastrodeagendamento.model.Cliente;


public class ClienteHelper {

    private ClienteActivity activity;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat dateFormatHora = new SimpleDateFormat("HH:MM");

    private EditText edtNome,edtData,edtFone;
    private Button btnSalvarCliente, btnFoto;
    private ImageView foto;

    private Spinner spinnerServico;
    private List<Servico> servicoAtendimento;
    private ArrayAdapter<Servico> adapter;
    private  Servico servicoAtendimentoSelecionado;

    private Cliente cliente;

    public ClienteHelper(final ClienteActivity activity) {
        this.activity = activity;
        servicoAtendimento = Arrays.asList(Servico.values());

        adapter= new ArrayAdapter(activity, android.R.layout.simple_list_item_1,servicoAtendimento );
        edtNome = (EditText) activity.findViewById(R.id.edtNome);
        edtData = (EditText) activity.findViewById(R.id.edtData);
        edtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(v);
            }
        });
        edtFone = (EditText) activity.findViewById(R.id.edtFone);
        spinnerServico = (Spinner) activity.findViewById(R.id.spinnerServico);
        spinnerServico.setAdapter(adapter);
        spinnerServico.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                servicoAtendimentoSelecionado = adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnSalvarCliente = (Button) activity.findViewById(R.id.btnSalvarCliente);
        btnSalvarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cliente = carregaDadosDaTela();
                if (validate()) {
                    ClienteDAO dao = new ClienteDAO(activity);
                    if (cliente.getId() == 0) {
                        dao.insert(cliente);
                    } else {
                        dao.update(cliente);
                    }
                    dao.close();
                    activity.finish();
                }
            }
        });
        foto = (ImageView) activity.findViewById(R.id.foto);
        btnFoto = (Button) activity.findViewById(R.id.formFotoButton);

        foto = (ImageView) activity.findViewById(R.id.foto);
        btnFoto = (Button) activity.findViewById(R.id.formFotoButton);

        cliente = (Cliente) activity.getIntent().getSerializableExtra("clienteSelecionado");
        if (cliente != null) {
            carregaDadosParaTela(cliente);
        }
        else {
            cliente = new Cliente();
        }
    }

    public Cliente carregaDadosDaTela() {
        cliente.setNome(edtNome.getText().toString());
        cliente.setCaminhoFoto((String) foto.getTag());
        cliente.setData(getDate());
        cliente.setFone(edtFone.getText().toString());
        cliente.setServicoAtendimento(servicoAtendimentoSelecionado);
        return cliente;
    }

    public void carregaDadosParaTela(Cliente cliente) {
        this.cliente = cliente;
        edtNome.setText(cliente.getNome());
        edtFone.setText(cliente.getFone());
        setImage(cliente.getCaminhoFoto());
        setDate(cliente.getData());
        spinnerServico.setSelection(servicoAtendimento.indexOf(cliente.getServicoAtendimento()));
    }

    public boolean validate() {
        boolean valid = true;
        if (edtNome.getText().toString().trim().isEmpty()) {
            edtNome.setError("Campo nome é obrigatório!");
            valid = false;
        }
        if (edtData.getText().toString().trim().isEmpty()) {
            edtData.setError("Campo data de agendamento é obrigatório!");
            valid = false;
        }
        return valid;
    }

    public Button getBtnFoto() {
        return btnFoto;
    }

    public void setImage(String localArquivoFoto) {
        if (localArquivoFoto != null) {
            Bitmap imagemFoto = BitmapFactory.decodeFile(localArquivoFoto);
            Bitmap imagemFotoReduzida = Bitmap
                    .createScaledBitmap(imagemFoto, imagemFoto.getWidth(),
                            300, true);
            foto.setImageBitmap(imagemFotoReduzida);
            foto.setTag(localArquivoFoto);
            foto.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        setDate(cal);
    }

    private void setDate(Calendar calendar) {
        try {
            edtData.setText(dateFormat.format(calendar.getTime()));
        }
        catch (Exception e) {
            edtData.setText(dateFormat.format(new Date()));
        }
    }

    private Calendar getDate() {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(dateFormat.parse(edtData.getText().toString()));
        }
        catch (Exception e) {
            c.setTime(new Date());
        }
        return c;
    }

    public void datePicker(View view){
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable("calendar", cliente.getData());
        fragment.setArguments(args);
        fragment.show(activity.getFragmentManager(), "");
    }
}
