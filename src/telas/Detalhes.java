package telas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.ModuloConexao;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Detalhes extends JFrame {
	
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	private JPanel contentPane;
	private JLabel l_elenco;
	private JLabel l_sinopse;
	private JLabel l_textduracao;
	private JLabel l_duracao;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Detalhes frame = new Detalhes();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public void getElenco(String idFilm) {
		String sql = "select elencoFilm from filmes where idFilm = ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, idFilm);
			rs = pst.executeQuery();
			if(rs.next()) {
				l_elenco.setText("<html>"+rs.getString(1)+"</html>");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			
		}
	}
	
	
	public void getSinopse(String idFilm) {
		String sql = "select sinopse from filmes where idFilm = ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, idFilm);
			rs = pst.executeQuery();
			if(rs.next()) {
				l_sinopse.setText("<html>"+rs.getString(1)+"</html>");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
	public void getDuracao(String idFilm) {
		String sql = "select duracaoFilm from filmes where idFilm = ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, idFilm);
			rs = pst.executeQuery();
			if(rs.next()) {
				l_duracao.setText(rs.getString(1));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
	
	
	public void fechar() {
		this.dispose();
	}
	

	/**
	 * Create the frame.
	 */
	public Detalhes() {
		conexao = ModuloConexao.conector();
		setTitle("Detalhes do filme");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 465, 347);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JButton b_sair = new JButton("Sair");
		rootPane.setDefaultButton(b_sair);
		
		JLabel l_textElenco = new JLabel("Elenco:");
		l_textElenco.setForeground(Color.BLUE);
		l_textElenco.setFont(new Font("Tahoma", Font.BOLD, 13));
		l_textElenco.setBounds(10, 48, 65, 14);
		contentPane.add(l_textElenco);
		
		l_elenco = new JLabel("");
		l_elenco.setBounds(20, 53, 429, 65);
		contentPane.add(l_elenco);
		
		JLabel l_textSinopse = new JLabel("Sinopse:");
		l_textSinopse.setForeground(Color.RED);
		l_textSinopse.setFont(new Font("Tahoma", Font.BOLD, 13));
		l_textSinopse.setBounds(10, 157, 86, 14);
		contentPane.add(l_textSinopse);
		
		l_sinopse = new JLabel("");
		l_sinopse.setBounds(10, 157, 429, 107);
		contentPane.add(l_sinopse);
		
		
		b_sair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fechar();
			}
		});
		b_sair.setBounds(179, 275, 89, 23);
		contentPane.add(b_sair);
		
		l_textduracao = new JLabel("Dura\u00E7\u00E3o:");
		l_textduracao.setForeground(Color.BLUE);
		l_textduracao.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_textduracao.setBounds(142, 11, 65, 14);
		contentPane.add(l_textduracao);
		
		l_duracao = new JLabel("");
		l_duracao.setBounds(198, 11, 131, 14);
		contentPane.add(l_duracao);
	}

}
