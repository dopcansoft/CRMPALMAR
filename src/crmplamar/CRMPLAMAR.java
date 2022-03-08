/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crmplamar;

import crmplamar.BO.SumatoriaVentasDiariasBO;
import crmplamar.identidad.CLIENTE;
import crmplamar.identidad.VENTA;
import crmplamar.identidad.inventario;
import crmplamar.identidad.notas_remision;
import crmplamar.identidad.Credito;
import crmplamar.identidad.pagos_credito;
import crmplamar.identidad.apartado;
import crmplamar.identidad.pagos_apartado;
import crmplamar.identidad.categoria;
import crmplamar.identidad.PROVEEDOR;
import crmplamar.identidad.detalle_venta;
import crmplamar.identidad.gasto;
import crmplamar.identidad.catalogoGasto;
import crmplamar.identidad.SUCURSAL;
import crmplamar.identidad.acumuladosVenta;
import crmplamar.identidad.ventasDiarias;
import crmplamar.identidad.compra;
import crmplamar.identidad.detalle_compra;
import crmplamar.identidad.detalle_traspaso;
import crmplamar.identidad.traspaso;
import crmplamar.identidad.usuario;
import crmplamar.identidad.bitacora_precio;

import crmplamar.DAO.catalogo_gastosDAO;
import crmplamar.DAO.inventarioDAO;
import crmplamar.DAO.proveedorDAO;
import crmplamar.DAO.clienteDAO;
import crmplamar.DAO.categoriaDAO;
import crmplamar.DAO.sucursalDAO;
import crmplamar.DAO.notas_remisionDAO;
import crmplamar.DAO.CreditoDAO;
import crmplamar.DAO.pagos_creditoDAO;
import crmplamar.DAO.apartadoDAO;
import crmplamar.DAO.pagos_apartadoDAO;
import crmplamar.DAO.compraDAO;
import crmplamar.DAO.detalle_compraDAO;
import crmplamar.DAO.detalle_ventaDAO;
import crmplamar.BO.VentaBO;
import crmplamar.DAO.gastoDAO;
import crmplamar.DAO.detalle_traspasoDAO;
import crmplamar.DAO.traspasoDAO;
import crmplamar.DAO.ventaDAO;
import crmplamar.DAO.usuarioDAO;
import crmplamar.DAO.bitacora_precioDAO;

import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.collections.ObservableArray;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;
import java.util.Locale;
import javafx.scene.control.ListView;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;
import java.awt.Dimension;
import java.io.FileReader;
import javafx.beans.value.ObservableStringValue;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javax.rmi.CORBA.Util;


/**
 *
 * @author dopcan
 */
public class CRMPLAMAR extends Application {
    String UsuarioActivo;
    usuario usrActivo;
    VBox vbAreaTrabajo = new VBox();
    HBox hbBarraEstado = new HBox();
    
    FileOutputStream fos;
    PrintStream Bitacora_flujo;
    
    TableView tvProveedores;
    inventarioDAO invent = new inventarioDAO();
    proveedorDAO provDAO = new proveedorDAO();
    clienteDAO cliDAO = new clienteDAO();
    categoriaDAO categDAO = new categoriaDAO();
    gastoDAO gasDAO = new gastoDAO();
    catalogo_gastosDAO catGastoDAO = new catalogo_gastosDAO();
    sucursalDAO sucDAO = new sucursalDAO();
    notas_remisionDAO notasDAO = new notas_remisionDAO();
    CreditoDAO creDao = new CreditoDAO();
    apartadoDAO apaDAO = new apartadoDAO();
    detalle_ventaDAO detventaDAO = new detalle_ventaDAO();
    pagos_apartadoDAO pagapaDAO = new pagos_apartadoDAO();
    pagos_creditoDAO pagcreDAO = new pagos_creditoDAO();
    detalle_traspasoDAO detraspDAO = new detalle_traspasoDAO();
    traspasoDAO traspDAO = new traspasoDAO();
    ventaDAO ventDAO = new ventaDAO();
    compraDAO compDAO = new compraDAO();
    detalle_compraDAO detCompDAO = new detalle_compraDAO();
    usuarioDAO userDAO = new usuarioDAO();
    bitacora_precioDAO bitacoraDAO = new bitacora_precioDAO();
       TextField tfdescuento = new TextField();
   Label lbDescuento = new Label();
   Button btntDescuento = new Button();
        
    
    CLIENTE clIdent = new CLIENTE();
    categoria catIdent = new categoria();
    notas_remision notRem = new notas_remision();
    
    ObservableList obList = FXCollections.observableArrayList();
    ObservableList<VENTA> lstventa = FXCollections.observableArrayList();
    ObservableList<detalle_venta> detventa = FXCollections.observableArrayList();
    ObservableList<detalle_traspaso> lstdetrasp = FXCollections.observableArrayList();
    ObservableList<traspaso> lstTrasp = FXCollections.observableArrayList();
    ObservableList<detalle_compra> lstDetcompra = FXCollections.observableArrayList();
    ObservableList<compra> lstCompra = FXCollections.observableArrayList();
    ObservableList<pagos_credito> lstPagosCre = FXCollections.observableArrayList();
    ObservableList<pagos_apartado> lstPagosApa = FXCollections.observableArrayList();
    ObservableList<gasto> gasList = FXCollections.observableArrayList();
    ObservableList<String> lstConceptosGastos = FXCollections.observableArrayList();
    ObservableList<notas_remision> lstNotasRem = FXCollections.observableArrayList();
    ObservableList<usuario> lstUsuarios = FXCollections.observableArrayList();
    ObservableList<bitacora_precio> lstBitacoraPrecio = FXCollections.observableArrayList();
    List<String> lstWhereConcepto = new ArrayList<>();
        TextField tfCodigoCliente = new TextField();
        TextField tfNombre = new TextField();
        TextField tfRazonSocial = new TextField();
        TextField tfDomicilioFiscal = new TextField();
        TextField tfTelefono = new TextField();
        TextField tfRFC = new TextField();
        TextField tfEmail = new TextField();
        Stage primarioStage;
    
    Menu mVentas = new Menu ("Notas de Ventas");
    MenuItem miCrearVentas = new MenuItem("Crear Venta..");
    MenuItem miCotizarVenta = new MenuItem("Cotizar Venta..");
    MenuItem miModificarVentas = new MenuItem("Modificar Venta..");
    MenuItem miConsultarVentas = new MenuItem("Consultar Venta..");
    MenuItem miEliminarVentas = new MenuItem("Eliminar Venta..");
    
    Menu mNotas = new Menu("Notas");
    MenuItem miModificarNotas = new MenuItem("Consultar/Modificar Notas..");

    
    Menu mInventario = new Menu("Inventario");
    MenuItem miNuevoProducto = new MenuItem("Nuevo Producto..");
    MenuItem miModificarProducto = new MenuItem("Modificar Producto..");
    MenuItem miEliminarProducto = new MenuItem("Eliminar Producto..");
    MenuItem miReportesInventario = new MenuItem("Reportes de Inventario");

    Menu mCompras = new Menu("Compras");
    MenuItem miNuevaComprar = new MenuItem("Comprar Producto");    
    MenuItem miModificarCompra = new MenuItem("Modificar/Consultar Compra Producto");
    MenuItem miEliminarCompra = new MenuItem("Eliminar Compra");
    
    Menu mTraspasarProducto = new Menu("Traspasar Producto ");
    MenuItem miConsultarTraspaso = new MenuItem("Consultar Traspaso ..");
    MenuItem miGenerarTraspaso = new MenuItem("Generar Traspaso ..");
    MenuItem miSumarExistencias = new MenuItem("Sumar Existencias ..");
    MenuItem miRestarEsistencias = new MenuItem("Restar Existencias ..");
    
    Menu mCategorias = new Menu("Categorias");
    MenuItem miNuevaCategoria = new MenuItem("Nueva Categoria ..");
    MenuItem miModificarCategoria = new MenuItem("Modificar Categoria  .. ");
    MenuItem miEliminarCategoria = new MenuItem("Eliminar Categoria ..");
    
    Menu mClientes = new Menu("Clientes");
    MenuItem miNuevoCliente = new MenuItem("Nuevo Cliente ..");
    MenuItem miModificarCliente = new MenuItem("Modificar datos de Cliente .. ");
    MenuItem miEliminarCliente = new MenuItem("Eliminar datos de Cliente ..");
    
    Menu mCreditos = new Menu("Creditos");
    MenuItem miPagarCreditos = new MenuItem("Gestion de Creditos");
    
    Menu mApartados = new Menu("Apartados");
    MenuItem miPagarApartados = new MenuItem("Gestion de Apartados");
    
    Menu mProveedores = new Menu("Proveedores");
    MenuItem miNuevoProveedor = new MenuItem("Nuevo Proveedor ..");
    MenuItem miModificarProveedor = new MenuItem("Modificar datos de Proveedor ..");
    MenuItem miEliminarProveedor = new MenuItem("Eliminar datos de Proveedor ..");    
    
    Menu mReporte = new Menu("Reporte");
    MenuItem miGenerarReporte = new MenuItem("Generar..");
    MenuItem mSubirReporte = new MenuItem("Subir Reporte");
    
    Menu mGastos = new Menu("Gastos");
    MenuItem miAgregarGastos = new MenuItem("Agregar Gastos");
    MenuItem miConsultarGastos = new MenuItem("Consulta Gastos");
    MenuItem miModificarGastos = new MenuItem("Modificar Gastos");
    MenuItem miEliminarGastos = new MenuItem("Eliminar Gastos");
    
    Menu mConceptos = new Menu("Catalogo Conceptos");
    MenuItem miAgregarConcepto =  new MenuItem("Agregar Concepto");
    MenuItem miModificarConcepto =  new MenuItem("Modificar Concepto");
    MenuItem miEliminarConcepto =  new MenuItem("Eliminar Concepto");
    
    Menu mAdmin = new Menu("Admin");
    MenuItem miIdentificarSucursal = new MenuItem("Identificar Sucursal..");
    MenuItem miCrearCuenta = new MenuItem("Crear Cuenta..");
    MenuItem miModificarCuenta = new MenuItem("Modificar Cuenta..");
    MenuItem miEliminarCuenta = new MenuItem("EliminarCuenta..");
    MenuItem miBitacoraPrecios = new MenuItem("Consultar Bitacora Precios..");
    
    Menu mHerramientas = new Menu("Herramientas");
    MenuItem miCrearDB = new MenuItem("Crear Base de Datos vacia..");
    MenuItem miEliminarDB = new MenuItem("Eliminar Base de Datos..");
    MenuItem miRespaldarDB = new MenuItem("Respaldar Base de Datos..");
    MenuItem miFullScreen = new MenuItem("Pantalla Completa");
    MenuItem miSalir = new MenuItem("Salir");

    public CRMPLAMAR() throws FileNotFoundException {
        LocalDateTime ldtFecha = LocalDateTime.now();
        this.fos = new FileOutputStream("Reportes/Bitacora/Bitacora_"+ldtFecha.getYear() 
                +ldtFecha.getMonthValue()
                +ldtFecha.getDayOfMonth()
                +ldtFecha.getHour()
                +ldtFecha.getMinute()
                +ldtFecha.getSecond()
                +".txt");
        Bitacora_flujo = new PrintStream(fos);
        System.setOut(Bitacora_flujo);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primarioStage = primaryStage;

        
        
        SeparatorMenuItem miSepCompras = new SeparatorMenuItem();
        SeparatorMenuItem miSepTraspasos = new SeparatorMenuItem();
        SeparatorMenuItem miSepCategorias = new SeparatorMenuItem();
        SeparatorMenuItem spCreditos = new SeparatorMenuItem();
        SeparatorMenuItem spApartados = new SeparatorMenuItem();
        SeparatorMenuItem spCatalogoGastos = new SeparatorMenuItem();
        
        miCrearVentas.setOnAction((ActionEvent e) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaCrearVenta());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaCrearVenta());
              }
        });
         
        miCotizarVenta.setOnAction((event) -> {
             if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaCrearCotizacion());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaCrearCotizacion());
              }
            
        });
        
        miModificarVentas.setOnAction((event) -> {
             if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaModificarVenta());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaModificarVenta());
              }
            
        });        
        
        miConsultarVentas.setOnAction((event) -> {
             if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaConsultarVenta());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaConsultarVenta());
              }
            
        });        
        
        miEliminarVentas.setOnAction((event) -> {
             if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaEliminarVenta());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaEliminarVenta());
              }
            
        });        
        
         miModificarNotas.setOnAction((event) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaModificarNotas());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaModificarNotas());
              }
             
         });
        
        mNotas.getItems().addAll(miModificarNotas);
        
        miNuevoProducto.setOnAction((ActionEvent e) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaNuevoProducto());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaNuevoProducto());
              }
        });
          
          miModificarProducto.setOnAction((ActionEvent e) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaModificarProducto());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaModificarProducto());
              }
        });
          
          miEliminarProducto.setOnAction((ActionEvent e) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaEliminarProducto());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaEliminarProducto());
              }
        });
        
        miReportesInventario.setOnAction((ActionEvent e) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaReportesProducto());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaReportesProducto());
              }
        });
        
          miNuevaComprar.setOnAction((ActionEvent e) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaNuevaCompra());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaNuevaCompra());
              }
            });
          
          
          miModificarCompra.setOnAction((ActionEvent e) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaModificarConsultarCompra());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaModificarConsultarCompra());
              }
            });
          
          miEliminarCompra.setOnAction((ActionEvent e) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaEliminarConsultarCompra());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaEliminarConsultarCompra());
              }
            });
          
            miConsultarTraspaso.setOnAction((ActionEvent e) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaConsultarTraspasos());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaConsultarTraspasos());
              }
            });
                    
            miGenerarTraspaso.setOnAction((ActionEvent e) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaGenerarTraspaso());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaGenerarTraspaso());
              }
            });
          
            miSumarExistencias.setOnAction((ActionEvent e) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaSumarInventarioconArchivo());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaSumarInventarioconArchivo());
              }
            });
          
            miRestarEsistencias.setOnAction((ActionEvent e) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaRestarInventarioconArchivo());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaRestarInventarioconArchivo());
              }
            });
          mTraspasarProducto.getItems().addAll(miConsultarTraspaso, miGenerarTraspaso, miSumarExistencias, miRestarEsistencias);          

           miNuevaCategoria.setOnAction((ActionEvent e)->{
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaNuevaCategoria());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaNuevaCategoria());
              }
           });
           
           miModificarCategoria.setOnAction((event) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaModificarCategoria());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaModificarCategoria());
              }
           });
           
           miEliminarCategoria.setOnAction((event) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaEliminarCategoria());

              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaEliminarCategoria());
              }                
           });
           
           miNuevoCliente.setOnAction((ActionEvent e) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaNuevoCliente());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaNuevoCliente());
              }
           });
           
           miModificarCliente.setOnAction((ActionEvent e)->{
               if (vbAreaTrabajo.getChildren().size()<=0){
                  vbAreaTrabajo.getChildren().add(vistaModificarCliente());
               }else{
                  vbAreaTrabajo.getChildren().remove(0);
                  vbAreaTrabajo.getChildren().add(vistaModificarCliente());
               }
           });
           
           
           miEliminarCliente.setOnAction((ActionEvent e)->{
               if (vbAreaTrabajo.getChildren().size() <= 0){
                   vbAreaTrabajo.getChildren().add(vistaEliminarCliente());
               }else{
                   vbAreaTrabajo.getChildren().remove(0);
                   vbAreaTrabajo.getChildren().add(vistaEliminarCliente());
               }
           });
                      
           miPagarCreditos.setOnAction((event) -> {
               if (vbAreaTrabajo.getChildren().size() <= 0){
                   vbAreaTrabajo.getChildren().add(vistaGestionCreditosCliente());
               }else{
                   vbAreaTrabajo.getChildren().remove(0);
                   vbAreaTrabajo.getChildren().add(vistaGestionCreditosCliente());
               }               
           });
           
           miPagarApartados.setOnAction((event) -> {
               if (vbAreaTrabajo.getChildren().size() <= 0){
                   vbAreaTrabajo.getChildren().add(vistaGestionApartadosCliente());
               }else{
                   vbAreaTrabajo.getChildren().remove(0);
                   vbAreaTrabajo.getChildren().add(vistaGestionApartadosCliente());
               }
               
           });
        
           miNuevoProveedor.setOnAction((ActionEvent e)->{
               if (vbAreaTrabajo.getChildren().size()<=0){
                   vbAreaTrabajo.getChildren().add(vistaNuevoProveedor());
               }else{
                   vbAreaTrabajo.getChildren().remove(0);
                   vbAreaTrabajo.getChildren().add(vistaNuevoProveedor());
               }
           });
           
           miModificarProveedor.setOnAction((ActionEvent e)->{
               if (vbAreaTrabajo.getChildren().size()<=0){
                   vbAreaTrabajo.getChildren().add(vistaModificarProveedor());
               }else{
                   vbAreaTrabajo.getChildren().remove(0);
                   vbAreaTrabajo.getChildren().add(vistaModificarProveedor());
               }
           });
           
           
           miEliminarProveedor.setOnAction((ActionEvent e)->{
               if (vbAreaTrabajo.getChildren().size()<=0){
                   vbAreaTrabajo.getChildren().add(vistaEliminarProveedor());
               }else{
                   vbAreaTrabajo.getChildren().remove(0);
                   vbAreaTrabajo.getChildren().add(vistaEliminarProveedor());
               }
           });

          miGenerarReporte.setOnAction((ActionEvent e)->{
              if(vbAreaTrabajo.getChildren().size()<=0){
                  vbAreaTrabajo.getChildren().add(vistaGenerarReporteGeneral());
              }else{
                  vbAreaTrabajo.getChildren().remove(0);
                  vbAreaTrabajo.getChildren().add(vistaGenerarReporteGeneral());
              }
          });
          
          mSubirReporte.setOnAction((ActionEvent e)->{
              if(vbAreaTrabajo.getChildren().size()<=0){
                  vbAreaTrabajo.getChildren().add(ventanaEnviarReporteDiarioGeneral());
              }else{
                  vbAreaTrabajo.getChildren().remove(0);
                  vbAreaTrabajo.getChildren().add(ventanaEnviarReporteDiarioGeneral());
              }
          });
          
        miAgregarGastos.setOnAction((event) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(ventanaAgregarGastos());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(ventanaAgregarGastos());
              }
        });
        
        miModificarGastos.setOnAction((event) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(ventanaModificarGastos());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(ventanaModificarGastos());
              }
        });
        
        miEliminarGastos.setOnAction((event) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(ventanaEliminarGastos());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(ventanaEliminarGastos());
              }
        });
        
        miConsultarGastos.setOnAction((event) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(ventanaConsultarGastos());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(ventanaConsultarGastos());
              }
        });
        
        miAgregarConcepto.setOnAction((event) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(ventanaAgregarConceptos());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(ventanaAgregarConceptos());
              }
        });
        
        miModificarConcepto.setOnAction((event) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(ventanaModificarConceptosGastos());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(ventanaModificarConceptosGastos());
              }
        });
        
        miEliminarConcepto.setOnAction((event) -> {
                          if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(ventanaEliminarConceptosGastos());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(ventanaEliminarConceptosGastos());
              }
        });
        
          miIdentificarSucursal.setOnAction((ActionEvent e)->{
              if(vbAreaTrabajo.getChildren().size()<=0){
                  vbAreaTrabajo.getChildren().add(vistaIdentificarSucursal());
              }else{
                  vbAreaTrabajo.getChildren().remove(0);
                  vbAreaTrabajo.getChildren().add(vistaIdentificarSucursal());
              }
          });
          
          miCrearCuenta.setOnAction((ActionEvent e)->{
              if(vbAreaTrabajo.getChildren().size()<=0){
                  vbAreaTrabajo.getChildren().add(vistaCrearCuenta());
              }else{
                  vbAreaTrabajo.getChildren().remove(0);
                  vbAreaTrabajo.getChildren().add(vistaCrearCuenta());
              }
          });
          
          miModificarCuenta.setOnAction((ActionEvent e)->{
              if(vbAreaTrabajo.getChildren().size()<=0){
                  vbAreaTrabajo.getChildren().add(vistaModificarCuenta());
              }else{
                  vbAreaTrabajo.getChildren().remove(0);
                  vbAreaTrabajo.getChildren().add(vistaModificarCuenta());
              }
          });
          
          miEliminarCuenta.setOnAction((ActionEvent e)->{
              if(vbAreaTrabajo.getChildren().size()<=0){
                  vbAreaTrabajo.getChildren().add(vistaEliminarCuenta());
              }else{
                  vbAreaTrabajo.getChildren().remove(0);
                  vbAreaTrabajo.getChildren().add(vistaEliminarCuenta());
              }
          });
          
          miBitacoraPrecios.setOnAction((ActionEvent e)->{
              if(vbAreaTrabajo.getChildren().size()<=0){
                  vbAreaTrabajo.getChildren().add(vistaConsultarBitacoraPrecios());
              }else{
                  vbAreaTrabajo.getChildren().remove(0);
                  vbAreaTrabajo.getChildren().add(vistaConsultarBitacoraPrecios());
              }
          });
          
         
          miSalir.setOnAction(((event) -> {
                primaryStage.close();
          }));
          
          miFullScreen.setOnAction((event) -> {
              primaryStage.setFullScreen(true);
          });

        mVentas.getItems().addAll(miCrearVentas,miConsultarVentas, miModificarVentas, miEliminarVentas,  miCotizarVenta);
        mCompras.getItems().addAll(miNuevaComprar, miModificarCompra, miEliminarCompra);
        mCategorias.getItems().addAll(miNuevaCategoria, miModificarCategoria, miEliminarCategoria);
        mInventario.getItems().addAll(miNuevoProducto, miModificarProducto, miEliminarProducto, miReportesInventario, miSepCompras, mCompras, miSepTraspasos, mTraspasarProducto, miSepCategorias, mCategorias );           
        mCreditos.getItems().addAll( miPagarCreditos);           
        mApartados.getItems().addAll(miPagarApartados);
        mClientes.getItems().addAll(miNuevoCliente, miModificarCliente, miEliminarCliente,
           spCreditos, mCreditos, spApartados, mApartados);           
        mProveedores.getItems().addAll(miNuevoProveedor, miModificarProveedor, miEliminarProveedor);          
        mReporte.getItems().addAll(miGenerarReporte, mSubirReporte);        
        mConceptos.getItems().addAll(miAgregarConcepto, miModificarConcepto, miEliminarConcepto);
        mGastos.getItems().addAll(miAgregarGastos, miModificarGastos, miEliminarGastos, miConsultarGastos, spCatalogoGastos, mConceptos);
        mAdmin.getItems().addAll(miIdentificarSucursal, miCrearCuenta, miModificarCuenta, miEliminarCuenta, miBitacoraPrecios);          
        //mHerramientas.getItems().addAll(miCrearDB, miEliminarDB, miRespaldarDB, miFullScreen, miSalir);
        mHerramientas.getItems().addAll(miFullScreen, miSalir);
        
        UsuarioActivo= "xxxx-xxxx";
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        double h= screenSize.getHeight();
        double w= screenSize.getWidth();        
        vbAreaTrabajo.setPrefSize(w, h-45);
        MenuBar mbPpal = new MenuBar(mVentas, mNotas, mInventario, mClientes, mProveedores, mReporte, mGastos, mAdmin,  mHerramientas);
        VBox vbPpal = new VBox(mbPpal);
        vbPpal.setPadding(new Insets(5,5,5,5));
        vbPpal.getChildren().addAll(vbAreaTrabajo, hbBarraEstado);
        
        StackPane root = new StackPane();
        root.getChildren().add(vbPpal);
        
        Scene scene = new Scene(root, 1200, 850);

        primaryStage.setTitle("Gestión Sucursales");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        loginEmpresa();

        //primaryStage.show();
    }
    private void removerVistas(){
       if (vbAreaTrabajo.getChildren().size()>0){
          vbAreaTrabajo.getChildren().remove(0);
       }
    }
        float MontoTotal = 0.0f;
    float Descuento = 0.0f;
   float MontoOriginal = 0.0f;
   
    private VBox vistaCrearVenta(){
        
         if (detventa.size()>0){
            detventa = FXCollections.observableArrayList();
        }
        
        tfdescuento.setText("");
        lbDescuento.setText("Descuento");
        btntDescuento.setText("Aplicar");
        tfCodigoCliente.setText("");
        tfNombre.setText("");
        tfRazonSocial.setText("");
        tfDomicilioFiscal.setText("");
        tfTelefono.setText("");
        tfRFC.setText("");
        tfEmail.setText(""); 
        
        
        VBox vbVistaPpal = new VBox();

        Label lbTituloVista = new Label("REGISTRAR UNA VENTA");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        //Etiquetas/datos de la Remisión
        Label lbFolio = new Label("Folio de Nota: ");
        Label lbFecha = new Label("Fecha: ");
        
        TextField tfFolio = new TextField();
        DatePicker dpFecha = new DatePicker(LocalDate.now());
        if(usrActivo.getTipo().equals("VENTA")){
          dpFecha.setEditable(false);
          dpFecha.setDisable(true);
        }
        //Etiquetas/Datos de la Venta
        Label lbEtiquetaMonto = new Label("TOTAL: $ ");
        Label lbMontoTotal = new Label("0.0");
        lbEtiquetaMonto.setFont(fuente);
        lbMontoTotal.setFont(fuente);
        tfdescuento.setText("0.0");
        
        btntDescuento.setOnAction((event) -> {
        
        Descuento = MontoOriginal / 100 * Float.parseFloat(tfdescuento.getText());
        MontoTotal = MontoOriginal - Descuento;
        lbMontoTotal.setText(String.valueOf(MontoTotal));
        });
        
	Label lbTipo_venta = new Label("Tipo de Venta: ");
        Label lbCodigoFactura = new Label("Codigo Factura");
        
	Label lbCantidad  = new Label("Cantidad");
	Label lbCodigoProd  = new Label("Codigo Producto: ");
        Label lbDescProducto = new Label ("Descripción Producto: ");
	Label lbPrecioMenudeo  = new Label("Precio Menudeo: ");
	Label lbPrecioMayoreo  = new Label("Precrio Mayoreo: ");
        Label lbPrecioVenta = new Label("Precio Venta");
        
        ObservableList<String> lstOpcionesTipoVenta = FXCollections.observableArrayList("EFECTIVO","VENTA A CREDITO", "APARTADO", "TARJETA", "TRANSFERENCIA");
        ComboBox cbTipoVenta = new ComboBox(lstOpcionesTipoVenta);
        cbTipoVenta.setPrefWidth(180);
        
        TextField tfCantidad = new TextField();
        tfCantidad.setMaxWidth(80);
        TextField tfCodigoFactura = new TextField();
        
        TextField tfCodigoProducto = new TextField();
        TextField tfDescrProd = new TextField();
        tfDescrProd.setMaxWidth(300);
        tfDescrProd.setPrefWidth(300);
        TextField tfPrecioMayor = new TextField();
        tfPrecioMayor.setMaxWidth(120);
        tfPrecioMayor.setPrefWidth(120);
        tfPrecioMayor.setEditable(false);
        TextField tfPrecioMenudeo = new TextField();
        tfPrecioMenudeo.setEditable(false);
        TextField tfPrecioVenta = new TextField();
        
        
        //Etiquetas/Datos de Cliente;
        Label lbCodigo_cliente  = new Label("Codigo_Cliente");
	Label lbNombre  = new Label("Nombre: ");
	Label lbRazon_social  = new Label("Razon Social");
	Label lbDomicilio_fiscal  = new Label("Dom. Fiscal");
	Label lbTelefono  = new Label("Telefono.");
	Label lbRFC  = new Label("RFC");
	Label lbEmail  = new Label("Email:");
        
        //Componentes de Interfaz
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgBusquedas = new ToggleGroup();
        
        RadioButton rbTodos = new RadioButton("Todos");
        RadioButton rbCodigo = new RadioButton("Codigo");
        RadioButton rbDescripcion = new RadioButton("Descripción");
        RadioButton rbCategoria = new RadioButton("Categoria");
        
        rbTodos.setSelected(true);
        
        rbTodos.setToggleGroup(tgBusquedas);
        rbCodigo.setToggleGroup(tgBusquedas);
        rbDescripcion.setToggleGroup(tgBusquedas);
        rbCategoria.setToggleGroup(tgBusquedas);
        
        ToggleGroup tgPrecio = new ToggleGroup();
        RadioButton rbPrecioMenudeo =  new RadioButton("Precio Menudeo");
        rbPrecioMenudeo.setOnAction((ActionEvent e)->{
            tfPrecioVenta.setText(tfPrecioMenudeo.getText());
        });
        
        RadioButton rbPrecioMayoreo = new RadioButton("Precio Mayoreo");
        rbPrecioMayoreo.setOnAction((ActionEvent e)->{
            tfPrecioVenta.setText(tfPrecioMayor.getText());
        });
                
        rbPrecioMenudeo.setToggleGroup(tgPrecio);
        rbPrecioMayoreo.setToggleGroup(tgPrecio);
        
       List<String> lstCategorias = new ArrayList<>();
       List<String> lstWherecat = new ArrayList<>();
       lstWherecat.add("id_categoria is not null");
       for (categoria i : categDAO.consultarCategoria(lstWherecat)){
            lstCategorias.add(i.getCategoria());
       }
        
        Label lbCodigo = new Label("Codigo: ");
        TextField tfCodigo = new TextField();
        Label lbDescripcion = new Label("Descripción: ");
        TextField tfDescripcion = new TextField();
        Label lbCategoria = new Label("Categoria: ");
        ComboBox cbCategoria = new ComboBox(FXCollections.observableArrayList(lstCategorias));
        cbCategoria.setPrefWidth(140);
        
        Label lbInventario = new Label("Tabla Inventario: ");
        TableView tvInventario = new TableView();
        tvInventario.setPrefHeight(350);
        tvInventario.setPrefWidth(550);
        
        TableColumn<inventario, Integer> claveProdColumna = new TableColumn<>("Codigo Producto");
        claveProdColumna.setMinWidth(120);
        claveProdColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));

        TableColumn<inventario, Integer> existenciaColumna = new TableColumn<>("Existencia");
        existenciaColumna.setMinWidth(120);
        existenciaColumna.setCellValueFactory(new PropertyValueFactory<>("existencia"));
        
        TableColumn<inventario, Integer> idUbicacionColumna = new TableColumn<>("Id Ubicación");
        idUbicacionColumna.setMinWidth(120);
        idUbicacionColumna.setCellValueFactory(new PropertyValueFactory<>("id_ubicacion"));
        
        TableColumn<inventario, Float> pMenudeoColumna = new TableColumn<>("Precio Menudeo");
        pMenudeoColumna.setMinWidth(120);
        pMenudeoColumna.setCellValueFactory(new PropertyValueFactory<>("precio_menudeo"));        

        TableColumn<inventario, Float> pMayoreoColumna = new TableColumn<>("Precio Mayoreo");
        pMayoreoColumna.setMinWidth(120);
        pMayoreoColumna.setCellValueFactory(new PropertyValueFactory<>("precio_mayoreo"));
        
        TableColumn<inventario, String> descripcionColumna = new TableColumn<>("Descripción");
        descripcionColumna.setMinWidth(120);
        descripcionColumna.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        
        TableColumn<inventario, String> uMedidaColumna = new TableColumn<>("Unidad Medidad");
        uMedidaColumna.setMinWidth(120);
        uMedidaColumna.setCellValueFactory(new PropertyValueFactory<>("unidad_medida"));

        TableColumn<inventario, Float> cCompraColumna = new TableColumn<>("Costo Compra");
        cCompraColumna.setMinWidth(120);
        cCompraColumna.setCellValueFactory(new PropertyValueFactory<>("costo_compra"));
        
        TableColumn<inventario, Integer> codProvColumna = new TableColumn<>("Codigo Proveedor");
        codProvColumna.setMinWidth(120);
        codProvColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prov"));
        
        tvInventario.getColumns().addAll(claveProdColumna, descripcionColumna, existenciaColumna,
                pMenudeoColumna, pMayoreoColumna, uMedidaColumna, idUbicacionColumna, 
                codProvColumna, cCompraColumna);
        List<String> lstWhere = new ArrayList<>();
        lstWhere.add("codigo_prod is not null");
        tvInventario.setItems(invent.consultarInventario(lstWhere));
        
        tvInventario.setOnMouseClicked((event) -> {
            inventario inv = new inventario();
            inv = (inventario) tvInventario.getSelectionModel().getSelectedItem();
            tfCodigoProducto.setText(String.valueOf(inv.getCodigo_prod()));
            tfDescrProd.setText(inv.getDescripcion());
            tfPrecioMenudeo.setText(String.valueOf(inv.getPrecio_menudeo()));
            tfPrecioMayor.setText(String.valueOf(inv.getPrecio_mayoreo()));
            rbPrecioMenudeo.setSelected(true);
            tfPrecioVenta.setText(tfPrecioMenudeo.getText());
        });
        
        tvInventario.setOnKeyPressed((event) ->{ 
            if(event.getCode()==KeyCode.ENTER){
                inventario inv = new inventario();
                inv = (inventario) tvInventario.getSelectionModel().getSelectedItem();
                tfCodigoProducto.setText(String.valueOf(inv.getCodigo_prod()));
                tfDescrProd.setText(inv.getDescripcion());
                tfPrecioMenudeo.setText(String.valueOf(inv.getPrecio_menudeo()));
                tfPrecioMayor.setText(String.valueOf(inv.getPrecio_mayoreo()));
                rbPrecioMenudeo.setSelected(true);
                tfPrecioVenta.setText(tfPrecioMenudeo.getText());
            }
        });
        MenuItem miEliminarDetVenta = new MenuItem("Eliminar");
        ContextMenu cmTabVentas = new ContextMenu();
        cmTabVentas.getItems().add(miEliminarDetVenta);
        
        Label lbProducto = new Label("Tabla Producto Seleccionados: ");
        TableView tvProductosSelecc = new TableView();
        tvProductosSelecc.setPrefHeight(350);
        tvProductosSelecc.setPrefWidth(550);
        //id_detalle_venta clave_prod cantidad codigo_nota_venta 
        //codigo_prod precio_menudeo precio_mayoreo descrprod 
        
        TableColumn<detalle_venta, Integer> idDetalleVentaColumna = new TableColumn<>("Id Detalle Venta");
        idDetalleVentaColumna.setMinWidth(120);
        idDetalleVentaColumna.setCellValueFactory(new PropertyValueFactory<>("id_detalle_venta"));
        
        TableColumn<detalle_venta, Integer> codigoProductColumna = new TableColumn<>("Codigo Producto");
        codigoProductColumna.setMinWidth(80);
        codigoProductColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));
        
        TableColumn<detalle_venta, String> descrProductColumna = new TableColumn<>("Descripción Producto");
        descrProductColumna.setMinWidth(220);
        descrProductColumna.setCellValueFactory(new PropertyValueFactory<>("descrprod"));
        
        TableColumn<detalle_venta, Integer> cantidadProductColumna = new TableColumn<>("Cantidad");
        cantidadProductColumna.setMinWidth(80);
        cantidadProductColumna.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        
        TableColumn<detalle_venta, Integer> codigoNotaVentaColumna = new TableColumn<>("Codigo Nota Venta");
        codigoNotaVentaColumna.setMinWidth(120);
        codigoNotaVentaColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_nota_venta"));

        TableColumn<detalle_venta, Integer> precioVentaColumna = new TableColumn<>("Precio Venta");
        precioVentaColumna.setMinWidth(120);
        precioVentaColumna.setCellValueFactory(new PropertyValueFactory<>("precio_venta"));
        
        TableColumn<detalle_venta, Integer> subtotalColumna = new TableColumn<>("Sub-total");
        subtotalColumna.setMinWidth(120);
        subtotalColumna.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
        
        tvProductosSelecc.getColumns().addAll(codigoProductColumna, descrProductColumna, cantidadProductColumna, precioVentaColumna, subtotalColumna);
        tvProductosSelecc.setItems(detventa);
        tvProductosSelecc.setContextMenu(cmTabVentas);
        miEliminarDetVenta.setOnAction((event) -> {
            tvProductosSelecc.getItems().remove(
                    tvProductosSelecc.getSelectionModel().getSelectedIndex());
            lbMontoTotal.setText("0.0");
            float MontoTotal = 0.0f;
            for (detalle_venta detv : detventa){
                MontoTotal = MontoTotal + detv.getSubTotal();
                lbMontoTotal.setText(Float.toString(MontoTotal));  
            }
        });       

        Button btnAgregarProducto = new Button("Agregar Producto");
        btnAgregarProducto.setOnAction((ActionEvent e)->{
            inventario inv = new inventario();
            inv = (inventario) tvInventario.getSelectionModel().getSelectedItem();
            if (tfCodigoProducto.getText().length()>0){
             if (tfCantidad.getText().length()>0){
                 if (inv.getExistencia()>= Integer.parseInt(tfCantidad.getText())){
                     if (tfPrecioVenta.getText().length()> 0){
                         detalle_venta detvta = new detalle_venta();
                         detvta.setCodigo_prod(Integer.parseInt(tfCodigoProducto.getText()));
                         detvta.setDescrprod(tfDescrProd.getText());
                         detvta.setCantidad(Integer.parseInt(tfCantidad.getText()));
                         detvta.setExistencia(inv.getExistencia());
                         float pventa = Float.parseFloat(tfPrecioVenta.getText());
                         if (rbPrecioMenudeo.isSelected()){
                             float pmenudeo = Float.parseFloat(tfPrecioMenudeo.getText());
                             if ( pventa != pmenudeo){
                                Alert alertmjs = new Alert(Alert.AlertType.CONFIRMATION);
                                alertmjs.setTitle("Mensaje de Confirmacion");
                                alertmjs.setContentText("El precio de venta es diferente del precio de menudeo, "
                                        + "Deseas almacenarlo como precio oficial?");
                                Optional<ButtonType> action = alertmjs.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    invent.modificarPrecioMenudeo(Integer.parseInt(tfCodigoProducto.getText()), pventa);
                                    LocalDateTime ldtUserActividad = LocalDateTime.now();
                                    System.out.println("Usuario:"+usrActivo.getNombre_completo()+"Fecha Hora: ");
                                    System.out.println("Modifico Inventario Precio Menudeo:\n precio"+pventa+"\n Producto: "
                                            +tfCodigoProducto.getText());
                                }
                                bitacora_precio bitCambioPrecio = new bitacora_precio();
                                bitCambioPrecio.setCodigo_prod(Integer.parseInt(tfCodigoProducto.getText()));
                                bitCambioPrecio.setId_usuario(usrActivo.getId_usuario());
                                bitCambioPrecio.setNombreUsuario(usrActivo.getNombre_completo());
                                bitCambioPrecio.setPrecio_menudeo_ant(Float.parseFloat(tfPrecioMenudeo.getText()));
                                bitCambioPrecio.setPrecio_menudeo(Float.parseFloat(tfPrecioVenta.getText()));
                                bitCambioPrecio.setFecha(LocalDateTime.now().toString());
                                bitCambioPrecio.setBandera(1);
                                bitacoraDAO.insertarBitacora(bitCambioPrecio);
                                    LocalDateTime ldtUserActividad = LocalDateTime.now();
                                    System.out.println("Usuario:"+usrActivo.getNombre_completo()+"Fecha Hora: ");
                                    System.out.println("Agrego Reg. Bitacora Precio:\n precio"+pventa+"\n Producto: "
                                            +tfCodigoProducto.getText());
                             } 
                         }
                         if (rbPrecioMayoreo.isSelected()){
                             float pmayoreo = Float.parseFloat(tfPrecioMayor.getText());
                             if ( pventa != pmayoreo){
                                Alert alertmjs = new Alert(Alert.AlertType.CONFIRMATION);
                                alertmjs.setTitle("Mensaje de Confirmacion");
                                alertmjs.setContentText("El precio de venta es diferente del precio de mayoreo, "
                                        + "Deseas almacenarlo como precio oficial?");
                                Optional<ButtonType> action = alertmjs.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    invent.modificarPrecioMayoreo(Integer.parseInt(tfCodigoProducto.getText()), pventa);
                                    LocalDateTime ldtUserActividad = LocalDateTime.now();
                                    System.out.println("Usuario:"+usrActivo.getNombre_completo()+"Fecha Hora: ");
                                    System.out.println("Modifico Inventario Precio Mayoreo:\n precio"+pventa+"\n Producto: "
                                            +tfCodigoProducto.getText());
                                }
                                bitacora_precio bitCambioPrecio = new bitacora_precio();
                                bitCambioPrecio.setCodigo_prod(Integer.parseInt(tfCodigoProducto.getText()));
                                bitCambioPrecio.setId_usuario(usrActivo.getId_usuario());
                                bitCambioPrecio.setNombreUsuario(usrActivo.getNombre_completo());
                                bitCambioPrecio.setPrecio_mayoreo_ant(Float.parseFloat(tfPrecioMayor.getText()));
                                bitCambioPrecio.setPrecio_mayoreo(pventa);
                                bitCambioPrecio.setFecha(LocalDateTime.now().toString());
                                bitCambioPrecio.setBandera(1);
                                bitacoraDAO.insertarBitacora(bitCambioPrecio);
                                    LocalDateTime ldtUserActividad = LocalDateTime.now();
                                    System.out.println("Usuario:"+usrActivo.getNombre_completo()+"Fecha Hora: ");
                                    System.out.println("Agrego Reg. Bitacora Precio:\n precio"+pventa+"\n Producto: "
                                            +tfCodigoProducto.getText());
                             } 
                         }
                            
                            detvta.setPrecio_venta(Float.parseFloat(tfPrecioVenta.getText()));
                            detvta.setSubTotal(Integer.parseInt(tfCantidad.getText())* Float.parseFloat(tfPrecioVenta.getText()));
                            float MontoTotal = Float.parseFloat(lbMontoTotal.getText());
                            MontoTotal = MontoTotal + Integer.parseInt(tfCantidad.getText())* Float.parseFloat(tfPrecioVenta.getText());
                            lbMontoTotal.setText(Float.toString(MontoTotal));
                            //MontoOriginal = Float.parseFloat(lbMontoTotal.getText());
                            MontoOriginal = MontoTotal;
                            detventa.add(detvta);
                            //System.out.println("Cuantos van:"+detventa.size());
                        }else {
                         Alert MensajeError = new Alert(Alert.AlertType.ERROR);
                         MensajeError.setTitle("Error");
                         MensajeError.setContentText("Falta Costo de Venta");
                         MensajeError.show();
                        }
                    }else {
                       Alert MensajeError = new Alert(Alert.AlertType.ERROR);
                       MensajeError.setTitle("Error");
                       MensajeError.setContentText("No puedes poner una cantidad mayor a la \n de la existencia en inventario");
                       MensajeError.show();
                    }
                }else {
               Alert MensajeError = new Alert(Alert.AlertType.ERROR);
               MensajeError.setTitle("Error");
               MensajeError.setContentText("Falta Cantidad de compra");
               MensajeError.show();
              }
            } else{
               Alert MensajeError = new Alert(Alert.AlertType.ERROR);
               MensajeError.setTitle("Error");
               MensajeError.setContentText("Falta Seleccionar Producto");
               MensajeError.show();           
            }
        });
        
        
        Button btnSeleccionarCliente = new Button("Seleccionar Cliente..");
        btnSeleccionarCliente.setOnAction((ActionEvent e)->{
            ventanaSeleccionarClientes();
        });
        
        GridPane gpDesscuento = new GridPane();
        gpDesscuento.add(lbDescuento, 0,0);
        gpDesscuento.add(tfdescuento, 1,0);
        gpDesscuento.add(btntDescuento, 1,1);
        gpDesscuento.setHgap(10);
        gpDesscuento.setVgap(10);
        
        GridPane gpClienteSeleccionado = new GridPane();
        gpClienteSeleccionado.setPadding(new Insets(5, 5, 5, 5));
        gpClienteSeleccionado.setVgap(10);
        gpClienteSeleccionado.setHgap(10);
        
        gpClienteSeleccionado.add(lbCodigo_cliente, 0, 0);
        gpClienteSeleccionado.add(tfCodigoCliente, 1, 0);
        gpClienteSeleccionado.add(lbNombre, 2, 0);
        gpClienteSeleccionado.add(tfNombre, 3, 0);
        
        gpClienteSeleccionado.add(lbRazon_social, 0, 1);
        gpClienteSeleccionado.add(tfRazonSocial, 1, 1);
        gpClienteSeleccionado.add(lbDomicilio_fiscal, 2, 1);
        gpClienteSeleccionado.add(tfDomicilioFiscal, 3, 1);
        
        gpClienteSeleccionado.add(lbTelefono, 0, 2);
        gpClienteSeleccionado.add(tfTelefono, 1, 2);
        gpClienteSeleccionado.add(lbRFC, 2, 2);
        gpClienteSeleccionado.add(tfRFC, 3, 2);
        
        gpClienteSeleccionado.add(lbEmail, 0, 3);
        gpClienteSeleccionado.add(tfEmail, 1, 3);
        
        
        VBox vbHead = new VBox();

        VBox vbClienteSeleccion = new VBox();
        vbClienteSeleccion.setSpacing(10);
        vbClienteSeleccion.getChildren().addAll(btnSeleccionarCliente, gpClienteSeleccionado);
        
        HBox hbTipoSeleccion = new HBox();
        hbTipoSeleccion.getChildren().addAll(rbTodos, rbCodigo, rbDescripcion, rbCategoria);
        hbTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        hbTipoSeleccion.setSpacing(5);
        
        VBox vbCodigo = new VBox();
        vbCodigo.getChildren().addAll(lbCodigo, tfCodigo);
        vbCodigo.setSpacing(5);
        
        VBox vbDesc = new VBox();
        vbDesc.getChildren().addAll(lbDescripcion, tfDescripcion);
        vbDesc.setSpacing(5);
        
        VBox vbCat = new VBox();
        vbCat.getChildren().addAll(lbCategoria, cbCategoria);
        vbCat.setSpacing(5);
        
        
        HBox hbCompSeleccion = new HBox();
        Button btnBuscarProductos = new Button("Seleccionar");
        btnBuscarProductos.setMaxHeight(50);
        btnBuscarProductos.setOnAction((ActionEvent e)->{
         List<inventario> lstInv = new ArrayList<>();
         List<String> lstWherelc = new ArrayList<>();
         
          if (rbTodos.isSelected()){
           lstWherelc.add("codigo_prod is not null");
           tvInventario.setItems(invent.consultarInventario(lstWherelc));
           //String titulo = "MODIFICAR PRODUCTOS ("+String.valueOf(tvInventario.getItems().size())+" Seleccionados)";
         } 
         
         if (rbCodigo.isSelected()){
           lstWhere.add("codigo_prod = "+tfCodigo.getText());
           //lstInv=invent.consultarInventario(lstWhere);
           //ObservableList obList = FXCollections.observableArrayList(lstInv);
           tvInventario.setItems(invent.consultarInventario(lstWhere));
         }
         if (rbDescripcion.isSelected()){
             
           lstWherelc.add("descripcion like '"+tfDescripcion.getText()+"%'");
           //lstInv=invent.consultarInventario(lstWhere);
           //ObservableList obList = FXCollections.observableArrayList(lstInv);
           tvInventario.setItems(invent.consultarInventario(lstWherelc));
         }
         
         if (rbCategoria.isSelected()){
           lstWherecat.add("id_categoria is not null");  
           for (categoria i : categDAO.consultarCategoria(lstWherecat)){
             String strCategoria =  i.getCategoria();
             if (strCategoria.compareTo(cbCategoria.getSelectionModel().getSelectedItem().toString())==0){
                  lstWhere.add("id_categoria = "+i.getId_categoria());
                  tvInventario.setItems(invent.consultarInventario(lstWhere));
             }
           }
         }
        });
        
        HBox hbTotal = new HBox();
        hbTotal.setPrefWidth(6000);
        hbTotal.setMaxWidth(600);
        hbTotal.setMinWidth(400);
        hbTotal.setSpacing(10);
        hbTotal.setHgrow(vbHead, Priority.ALWAYS);
        hbTotal.setAlignment(Pos.CENTER_RIGHT);
        hbTotal.getChildren().addAll(lbEtiquetaMonto, lbMontoTotal, gpDesscuento);
        
        hbCompSeleccion.getChildren().addAll(vbCodigo, vbDesc, vbCat, btnBuscarProductos, hbTotal);
        hbCompSeleccion.setPadding(new Insets(10, 10, 10, 10));
        hbCompSeleccion.setSpacing(5);

        Separator spSeleccionProductos = new Separator();
        vbHead.getChildren().addAll(lbTipoBusqueda, hbTipoSeleccion, hbCompSeleccion, spSeleccionProductos);
        
        VBox vbTabInventario = new VBox();
        vbTabInventario.setPadding(new Insets(5, 5, 5, 5));
        
        
        vbTabInventario.getChildren().addAll(lbInventario, tvInventario, lbProducto, tvProductosSelecc);
        
        Separator spVenta = new Separator();
        Separator spProducto =  new Separator();
        
        GridPane gpBloqueVenta = new GridPane();
        gpBloqueVenta.setPadding(new Insets(5, 5, 5, 5));
        gpBloqueVenta.setVgap(10);
        gpBloqueVenta.setHgap(10);
        
        gpBloqueVenta.add(lbTipo_venta, 0, 0);
        gpBloqueVenta.add(cbTipoVenta , 1, 0);

        gpBloqueVenta.add(lbFecha , 2, 0);
        gpBloqueVenta.add(dpFecha , 3, 0);

        
        gpBloqueVenta.add(lbCodigoFactura , 0, 1);
        gpBloqueVenta.add(tfCodigoFactura , 1, 1);
               
        gpBloqueVenta.add(lbFolio , 2, 1);
        gpBloqueVenta.add(tfFolio, 3, 1);

        GridPane gpBloqueProducto = new GridPane();
        gpBloqueProducto.setPadding(new Insets(5, 5, 5, 5));
        gpBloqueProducto.setVgap(10);
        gpBloqueProducto.setHgap(10);
        
        gpBloqueProducto.add(lbCodigoProd , 0, 0);
        gpBloqueProducto.add(tfCodigoProducto , 1, 0);
        
        gpBloqueProducto.add(lbCantidad , 2, 0);
        gpBloqueProducto.add(tfCantidad , 3, 0);
        
        gpBloqueProducto.add(lbDescProducto , 2, 1);
        gpBloqueProducto.add(tfDescrProd , 3, 1);
        
        gpBloqueProducto.add(rbPrecioMenudeo, 0, 2);
        gpBloqueProducto.add(rbPrecioMayoreo, 2, 2);

        gpBloqueProducto.add(lbPrecioMenudeo , 0, 3);
        gpBloqueProducto.add(tfPrecioMenudeo , 1, 3);

        gpBloqueProducto.add(lbPrecioMayoreo , 2, 3);
        gpBloqueProducto.add(tfPrecioMayor , 3, 3);
        
        gpBloqueProducto.add(lbPrecioVenta, 0, 4);
        gpBloqueProducto.add(tfPrecioVenta, 1, 4);
        
        gpBloqueProducto.add(btnAgregarProducto, 2, 4);

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        
        Button btnGuardar = new Button("Registrar Venta");
        btnGuardar.setOnAction((event) -> {
           if (tfCodigoCliente.getText().length()>0){
               if (!detventa.isEmpty()){
                   if (!cbTipoVenta.getSelectionModel().isEmpty()){
                       //System.out.println("Longitud Tipo Venta -->"+ cbTipoVenta.getValue().toString().length());
                       if (tfFolio.getText().length()>0){
                           VENTA vta = new VENTA();
                           vta.setCodigo_cliente(Integer.parseInt(tfCodigoCliente.getText()));
                           vta.setCodigo_factura(tfCodigoFactura.getText());
                           vta.setCodigo_nota_venta(0);
                           vta.setFecha(dpFecha.getValue().toString());
                           if (tfCodigoFactura.getText().isEmpty() && cbTipoVenta.getValue().toString().compareTo("EFECTIVO")==0 )
                               vta.setTipo_venta("REMISION "+cbTipoVenta.getValue().toString());
                           else if (!tfCodigoFactura.getText().isEmpty() && cbTipoVenta.getValue().toString().compareTo("EFECTIVO")==0 )   
                               vta.setTipo_venta("SISTEMA "+cbTipoVenta.getValue().toString());
                           else vta.setTipo_venta(cbTipoVenta.getValue().toString());
            
                           notas_remision notaRem = new notas_remision();
                           notaRem.setBandera(1);
                           notaRem.setFecha(dpFecha.getValue().toString());
                           notaRem.setFolio(tfFolio.getText());
                           notaRem.setMonto(Float.parseFloat(lbMontoTotal.getText()));
                           if (tfCodigoFactura.getText().isEmpty() && cbTipoVenta.getValue().toString().compareTo("EFECTIVO")==0 )
                             notaRem.setTipo_operacion("REMISION "+cbTipoVenta.getValue().toString());
                           else if (!tfCodigoFactura.getText().isEmpty() && cbTipoVenta.getValue().toString().compareTo("EFECTIVO")==0 )
                             notaRem.setTipo_operacion("SISTEMA "+cbTipoVenta.getValue().toString());
                           else notaRem.setTipo_operacion(cbTipoVenta.getValue().toString());
                           notaRem.setDescuento(Float.parseFloat(tfdescuento.getText()));
                           System.out.println(Float.parseFloat(tfdescuento.getText()));
                           Credito cred = new Credito();
                           cred.setBandera(1);
                           cred.setCodigo_cliente(Integer.parseInt(tfCodigoCliente.getText()));
                           cred.setMonto(Float.parseFloat(lbMontoTotal.getText()));
                           cred.setFecha(dpFecha.getValue().toString());
            
                           apartado apart = new apartado();
                           apart.setBandera(1);
                           apart.setCodigo_cliente(Integer.parseInt(tfCodigoCliente.getText()));
                           apart.setMonto(Float.parseFloat(lbMontoTotal.getText()));
                           System.out.println("Monto Apartado: "+ apart.getMonto());
                           apart.setFecha(dpFecha.getValue().toString());
            
                           VentaBO ventBo = new VentaBO();
                           String strTipoVenta = cbTipoVenta.getSelectionModel().getSelectedItem().toString();
                           if (strTipoVenta.compareTo("EFECTIVO")==0){
                                    LocalDateTime ldtUserActividad = LocalDateTime.now();
                                    System.out.println("Usuario:"+usrActivo.getNombre_completo()+"Fecha Hora: "+ldtUserActividad.toString());
                                ventBo.guardarVenta(vta, notaRem, cred, apart, detventa, 0);
                                Alert altMensaje = new Alert(Alert.AlertType.INFORMATION);
                                altMensaje.setContentText("Venta en Efectivo Registrada");
                                altMensaje.setTitle("Informacion-Venta");
                                altMensaje.show();
                            } // "Transferencia"
                           if (strTipoVenta.compareTo("VENTA A CREDITO")==0){
                                    LocalDateTime ldtUserActividad = LocalDateTime.now();
                                    System.out.println("Usuario:"+usrActivo.getNombre_completo()+"Fecha Hora: "+ldtUserActividad.toString());
                                   ventBo.guardarVenta(vta, notaRem, cred, apart, detventa, 1);
                                   Alert altMensaje = new Alert(Alert.AlertType.INFORMATION);
                                   altMensaje.setContentText("Venta a Credito Registrada");
                                   altMensaje.setTitle("Informacion-Venta");
                                   altMensaje.show();
                            }            
                            if (strTipoVenta.compareTo("APARTADO")==0){
                                    LocalDateTime ldtUserActividad = LocalDateTime.now();
                                    System.out.println("Usuario:"+usrActivo.getNombre_completo()+"Fecha Hora: "+ldtUserActividad.toString());
                                  ventBo.guardarVenta(vta, notaRem, cred, apart, detventa, 2);
                                  Alert altMensaje =new Alert(Alert.AlertType.INFORMATION);
                                  altMensaje.setContentText("Venta en Apartado Registrada");
                                  altMensaje.setTitle("Informacion-Venta");
                                  altMensaje.show();
                            } 
                            if (strTipoVenta.compareTo("TARJETA")==0){
                                    LocalDateTime ldtUserActividad = LocalDateTime.now();
                                    System.out.println("Usuario:"+usrActivo.getNombre_completo()+"Fecha Hora: "+ldtUserActividad.toString());
                                  ventBo.guardarVenta(vta, notaRem, cred, apart, detventa, 3);
                                  Alert altMensaje =new Alert(Alert.AlertType.INFORMATION);
                                  altMensaje.setContentText("Venta Con Tarjeta de credito Registrada");
                                  altMensaje.setTitle("Informacion-Venta");
                                  altMensaje.show();
                            } 
                            if (strTipoVenta.compareTo("TRANSFERENCIA")==0){
                                    LocalDateTime ldtUserActividad = LocalDateTime.now();
                                    System.out.println("Usuario:"+usrActivo.getNombre_completo()+"Fecha Hora: "+ldtUserActividad.toString());
                                   ventBo.guardarVenta(vta, notaRem, cred, apart, detventa, 4);
                                   Alert altMensaje =new Alert(Alert.AlertType.INFORMATION);
                                   altMensaje.setContentText("Venta con Transferencia Registrada");
                                   altMensaje.setTitle("Informacion-Venta");
                                   altMensaje.show();
                            }
                            removerVistas();
                       }else{
                        Alert MensajeError = new Alert(Alert.AlertType.ERROR);
                        MensajeError.setTitle("Error");
                        MensajeError.setContentText("Falta Folio de Remision");
                        MensajeError.show();             
                       }
                    }else{
                        Alert MensajeError = new Alert(Alert.AlertType.ERROR);
                        MensajeError.setTitle("Error");
                        MensajeError.setContentText("Falta Tipo de Venta");
                        MensajeError.show();             
                    }
                }else{
                        Alert MensajeError = new Alert(Alert.AlertType.ERROR);
                        MensajeError.setTitle("Error");
                        MensajeError.setContentText("Falta Seleccionar Producto");
                        MensajeError.show();             
                }
            }else {
                    Alert MensajeError = new Alert(Alert.AlertType.ERROR);
                    MensajeError.setTitle("Error");
                    MensajeError.setContentText("Falta Seleccionar Cliente");
                    MensajeError.show();
            }
        });
        
        HBox hbBotonesInferiores = new HBox();
        hbBotonesInferiores.setAlignment(Pos.CENTER_RIGHT);
        hbBotonesInferiores.getChildren().addAll(btnCancelar, btnGuardar);
        
        VBox vbTabProducto = new VBox();
        vbTabProducto.setSpacing(5);
        vbTabProducto.getChildren().addAll(vbClienteSeleccion, spVenta, gpBloqueVenta, spProducto, gpBloqueProducto, hbBotonesInferiores);
        
        
        HBox hbBody = new HBox();
        hbBody.getChildren().addAll(vbTabInventario, vbTabProducto);
        
        vbVistaPpal.getChildren().addAll(lbTituloVista, vbHead, hbBody);
        
        return vbVistaPpal; 
    }
    private VBox vistaConsultarVenta(){
        
         if (detventa.size()>0){
            detventa = FXCollections.observableArrayList();
        }
        List<String> lstWhere = new ArrayList<>();
        tfCodigoCliente.setText("");
        tfNombre.setText("");
        tfRazonSocial.setText("");
        tfDomicilioFiscal.setText("");
        tfTelefono.setText("");
        tfRFC.setText("");
        tfEmail.setText("");       
        
        VBox vbVistaPpal = new VBox();

        Label lbTituloVista = new Label("CONSULTAR DETALLES DE VENTA");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        //Etiquetas/datos de la Remisión
        Label lbFolio = new Label("Folio de Nota: ");
        Label lbFecha = new Label("Fecha: ");
        
        TextField tfFolio = new TextField();
        DatePicker dpFecha = new DatePicker(LocalDate.now());
        
        //Etiquetas/Datos de la Venta
        Label lbEtiquetaMonto = new Label("TOTAL: $ ");
        Label lbMontoTotal = new Label("0.0");
        lbEtiquetaMonto.setFont(fuente);
        lbMontoTotal.setFont(fuente);
        
        //Componentes para busqueda de ventas
        Label lbBuscarPorFecha = new  Label("Fecha:");
        Label lbBuscarPorTipo = new Label("Tipo Venta :");
        Label lbBuscarPorProducto = new Label("Producto :");
        DatePicker dpBuscarPorFecha = new DatePicker(LocalDate.now());
        TextField tfBuscarPorTipo = new TextField();
        TextField tfBuscarPorProducto = new TextField();
        Button btnBuscarVenta = new Button("Buscar Ventas");

        
        RadioButton rbBuscarPorFecha = new RadioButton("Buscar por Fecha");
        RadioButton rbBuscarPorTipo = new RadioButton("Buscar por Tipo Venta");
        RadioButton rbBuscarPorProducto = new RadioButton("Buscar por Producto");
        
        ToggleGroup tgOpcionesBusqueda = new ToggleGroup();
        
        rbBuscarPorFecha.setToggleGroup(tgOpcionesBusqueda);
        rbBuscarPorTipo.setToggleGroup(tgOpcionesBusqueda);
        rbBuscarPorProducto.setToggleGroup(tgOpcionesBusqueda);
        
        rbBuscarPorFecha.setSelected(true);
        
        TableView tvTablaVentas = new TableView();
        
        TableColumn<VENTA, Integer> idVentaColumna = new TableColumn<>("Id Venta");
        idVentaColumna.setMinWidth(120);
        idVentaColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_nota_venta")); 
        
        TableColumn<VENTA, String> FechaVentaColumna = new TableColumn<>("Fecha");
        FechaVentaColumna.setMinWidth(120);
        FechaVentaColumna.setCellValueFactory(new PropertyValueFactory<>("fecha")); 
        
        TableColumn<VENTA, String> tipoVentaColumna = new TableColumn<>("Tipo");
        tipoVentaColumna.setMinWidth(120);
        tipoVentaColumna.setCellValueFactory(new PropertyValueFactory<>("tipo_venta"));
        
        TableColumn<VENTA, Integer> clienteVentaColumna = new TableColumn<>("Codigo Cliente");
        clienteVentaColumna.setMinWidth(120);
        clienteVentaColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_cliente"));        
        
        tvTablaVentas.getColumns().addAll(idVentaColumna, FechaVentaColumna, tipoVentaColumna, clienteVentaColumna);

        MenuItem miEliminarDetVenta = new MenuItem("Eliminar");

        ContextMenu cmTabVentas = new ContextMenu();
        cmTabVentas.getItems().add(miEliminarDetVenta);
        
        btnBuscarVenta.setOnAction((event) -> {
            if (!lstventa.isEmpty()){
                lstventa.clear();
            }
            if (rbBuscarPorFecha.isSelected()==true){
                  lstWhereConcepto.clear();
                  lstWhereConcepto.add("fecha = '"+dpBuscarPorFecha.getValue().toString()+"' ");
                  lstventa = FXCollections.observableArrayList(ventDAO.consultaVenta(lstWhereConcepto));
                  tvTablaVentas.setItems(lstventa);
            }
            
            if (rbBuscarPorTipo.isSelected()==true){
                  lstWhereConcepto.clear();
                  lstWhereConcepto.add("tipo_venta = '"+tfBuscarPorTipo.getText()+"' ");
                  lstventa = FXCollections.observableArrayList(ventDAO.consultaVenta(lstWhereConcepto));
                  tvTablaVentas.setItems(lstventa);
            }
            
            if (rbBuscarPorProducto.isSelected()==true){
                  if(!detventa.isEmpty())detventa.clear();
                  lstWhere.clear();
                  lstWhere.add("descrprod like '%"+tfBuscarPorProducto.getText()+"%' ");
                  List<detalle_venta> lstDetVentaTemp = new ArrayList<>();
                  lstDetVentaTemp= detventaDAO.consultaDistinctDetVenta(lstWhere);
                  if(!lstventa.isEmpty())lstventa.clear();
                  for(detalle_venta detcTemp:lstDetVentaTemp){
                       lstWhereConcepto.clear();
                       lstWhereConcepto.add("codigo_nota_venta = "+detcTemp.getCodigo_nota_venta());                      
                       lstventa.add(ventDAO.consultaVenta(lstWhereConcepto).get(0));
                  }
            tvTablaVentas.setItems(lstventa);
            }
        });
        
        GridPane gpCBV = new GridPane();
        gpCBV.setHgap(10);
        gpCBV.setVgap(5);
        gpCBV.add(rbBuscarPorFecha, 0, 0);
        gpCBV.add(rbBuscarPorTipo, 1, 0);
        gpCBV.add(rbBuscarPorProducto, 2, 0);
        gpCBV.add(lbBuscarPorFecha, 0, 1);
        gpCBV.add(dpBuscarPorFecha, 1, 1);
        gpCBV.add(lbBuscarPorTipo, 2, 1);
        gpCBV.add(tfBuscarPorTipo, 3, 1);
        gpCBV.add(lbBuscarPorProducto, 4, 1);
        gpCBV.add(tfBuscarPorProducto, 5, 1);
        gpCBV.add(btnBuscarVenta, 6, 1);
        
        Pane tvPane = new Pane();

	Label lbTipo_venta = new Label("Tipo de Venta: ");
        Label lbCodigoFactura = new Label("Codigo Factura");
              
        ObservableList<String> lstOpcionesTipoVenta = FXCollections.observableArrayList("EFECTIVO","VENTA A CREDITO", "APARTADO", "TARJETA", "TRANSFERENCIA");
        ComboBox cbTipoVenta = new ComboBox(lstOpcionesTipoVenta);
        cbTipoVenta.setPrefWidth(180);
        
        TextField tfCantidad = new TextField();
        tfCantidad.setMaxWidth(80);
        TextField tfCodigoFactura = new TextField();
        
        TextField tfCodigoProducto = new TextField();
        TextField tfDescrProd = new TextField();
        tfDescrProd.setMaxWidth(300);
        tfDescrProd.setPrefWidth(300);
        TextField tfPrecioMayor = new TextField();
        tfPrecioMayor.setMaxWidth(120);
        tfPrecioMayor.setPrefWidth(120);
        tfPrecioMayor.setEditable(false);
        TextField tfPrecioMenudeo = new TextField();
        tfPrecioMenudeo.setEditable(false);
        TextField tfPrecioVenta = new TextField();
        
        
        //Etiquetas/Datos de Cliente;
        Label lbCodigo_cliente  = new Label("Codigo_Cliente");
	Label lbNombre  = new Label("Nombre: ");
	Label lbRazon_social  = new Label("Razon Social");
	Label lbDomicilio_fiscal  = new Label("Dom. Fiscal");
	Label lbTelefono  = new Label("Telefono.");
	Label lbRFC  = new Label("RFC");
	Label lbEmail  = new Label("Email:");
        
        //Componentes de Interfaz

        Label lbProducto = new Label("Tabla Producto Seleccionados: ");
        TableView tvProductosSelecc = new TableView();
        tvProductosSelecc.setPrefHeight(350);
        tvProductosSelecc.setPrefWidth(550);
        tvProductosSelecc.setContextMenu(cmTabVentas);
        //id_detalle_venta clave_prod cantidad codigo_nota_venta 
        //codigo_prod precio_menudeo precio_mayoreo descrprod 
        
        TableColumn<detalle_venta, Integer> idDetalleVentaColumna = new TableColumn<>("Id Detalle Venta");
        idDetalleVentaColumna.setMinWidth(120);
        idDetalleVentaColumna.setCellValueFactory(new PropertyValueFactory<>("id_detalle_venta"));
        
        TableColumn<detalle_venta, Integer> codigoProductColumna = new TableColumn<>("Codigo Producto");
        codigoProductColumna.setMinWidth(80);
        codigoProductColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));
        
        TableColumn<detalle_venta, String> descrProductColumna = new TableColumn<>("Descripción Producto");
        descrProductColumna.setMinWidth(220);
        descrProductColumna.setCellValueFactory(new PropertyValueFactory<>("descrprod"));
        
        TableColumn<detalle_venta, Integer> cantidadProductColumna = new TableColumn<>("Cantidad");
        cantidadProductColumna.setMinWidth(80);
        cantidadProductColumna.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        
        TableColumn<detalle_venta, Integer> codigoNotaVentaColumna = new TableColumn<>("Codigo Nota Venta");
        codigoNotaVentaColumna.setMinWidth(120);
        codigoNotaVentaColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_nota_venta"));

        TableColumn<detalle_venta, Integer> precioVentaColumna = new TableColumn<>("Precio Venta");
        precioVentaColumna.setMinWidth(120);
        precioVentaColumna.setCellValueFactory(new PropertyValueFactory<>("precio_venta"));
        
        TableColumn<detalle_venta, Integer> subtotalColumna = new TableColumn<>("Sub-total");
        subtotalColumna.setMinWidth(120);
        subtotalColumna.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
        
        tvProductosSelecc.getColumns().addAll(codigoProductColumna, descrProductColumna, cantidadProductColumna, precioVentaColumna, subtotalColumna);
        tvProductosSelecc.setItems(detventa);
        
        VBox vbBuscarVenta = new VBox();
        vbBuscarVenta.setSpacing(5);
        vbBuscarVenta.getChildren().addAll(lbProducto, tvProductosSelecc);
        
        tvTablaVentas.setOnMouseClicked((event) -> {
            
            VENTA vtaTemp = (VENTA) tvTablaVentas.getSelectionModel().getSelectedItem();
            lstWhereConcepto.clear();
            lstWhereConcepto.add("codigo_cliente = "+vtaTemp.getCodigo_cliente());
            CLIENTE cliIden = cliDAO.consultarClientes(lstWhereConcepto).get(0);
            tfCodigoCliente.setText(String.valueOf(cliIden.getCodigo_cliente()));
            tfNombre.setText(cliIden.getNombre());
            tfRazonSocial.setText(cliIden.getRazon_social());
            tfDomicilioFiscal.setText(cliIden.getDomicilio_fiscal());
            tfEmail.setText(cliIden.getEmail());
            tfTelefono.setText(cliIden.getTelefono());
            tfRFC.setText(cliIden.getRfc());
            cbTipoVenta.setValue(vtaTemp.getTipo_venta());
            dpFecha.setValue(LocalDate.parse(vtaTemp.getFecha()));
            tfCodigoFactura.setText(vtaTemp.getCodigo_factura());
            lstWhereConcepto.clear();
            lstWhereConcepto.add("id_nota_rem = "+vtaTemp.getId_nota_rem());
            notas_remision notasRemTemp = notasDAO.consultarNotasRem(lstWhereConcepto).get(0);
            tfFolio.setText(String.valueOf(notasRemTemp.getFolio()));
            if(!detventa.isEmpty()){
               detventa.clear();
            }
            lstWhereConcepto.clear();
            lstWhereConcepto.add("codigo_nota_venta = "+vtaTemp.getCodigo_nota_venta());
            detventa = FXCollections.observableArrayList(detventaDAO.consultaDetVenta(lstWhereConcepto));
            lbMontoTotal.setText("0.0");
            for (detalle_venta detv : detventa){
                lstWhere.clear();
                lstWhere.add("codigo_prod = "+detv.getCodigo_prod());
                inventario ivTemp = invent.consultaInventario(lstWhere).get(0);
                detv.setExistencia(ivTemp.getExistencia());
                detv.setSubTotal(detv.getCantidad()* detv.getPrecio_venta());
                float MontoTotal = Float.parseFloat(lbMontoTotal.getText());
                MontoTotal = MontoTotal + detv.getSubTotal();
                lbMontoTotal.setText(Float.toString(MontoTotal));               
            }
            tvProductosSelecc.setItems(detventa);
        });        
        
        GridPane gpClienteSeleccionado = new GridPane();
        gpClienteSeleccionado.setPadding(new Insets(5, 5, 5, 5));
        gpClienteSeleccionado.setVgap(10);
        gpClienteSeleccionado.setHgap(10);
        
        gpClienteSeleccionado.add(lbCodigo_cliente, 0, 0);
        gpClienteSeleccionado.add(tfCodigoCliente, 1, 0);
        gpClienteSeleccionado.add(lbNombre, 2, 0);
        gpClienteSeleccionado.add(tfNombre, 3, 0);
        
        gpClienteSeleccionado.add(lbRazon_social, 0, 1);
        gpClienteSeleccionado.add(tfRazonSocial, 1, 1);
        gpClienteSeleccionado.add(lbDomicilio_fiscal, 2, 1);
        gpClienteSeleccionado.add(tfDomicilioFiscal, 3, 1);
        
        gpClienteSeleccionado.add(lbTelefono, 0, 2);
        gpClienteSeleccionado.add(tfTelefono, 1, 2);
        gpClienteSeleccionado.add(lbRFC, 2, 2);
        gpClienteSeleccionado.add(tfRFC, 3, 2);
        
        gpClienteSeleccionado.add(lbEmail, 0, 3);
        gpClienteSeleccionado.add(tfEmail, 1, 3);
        
        VBox vbHead = new VBox();

        /*VBox vbClienteSeleccion = new VBox();
        vbClienteSeleccion.setSpacing(10);
        vbClienteSeleccion.getChildren().addAll(gpClienteSeleccionado);*/
        
        HBox hbTipoSeleccion = new HBox();
        hbTipoSeleccion.getChildren().addAll(gpCBV);
        hbTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        hbTipoSeleccion.setSpacing(5);
        
        HBox hbCompSeleccion = new HBox();

        GridPane gpBloqueVenta = new GridPane();
        gpBloqueVenta.setPadding(new Insets(5, 5, 5, 5));
        gpBloqueVenta.setVgap(10);
        gpBloqueVenta.setHgap(10);
        
        gpBloqueVenta.add(lbTipo_venta, 0, 0);
        gpBloqueVenta.add(cbTipoVenta , 1, 0);

        gpBloqueVenta.add(lbFecha , 2, 0);
        gpBloqueVenta.add(dpFecha , 3, 0);

        
        gpBloqueVenta.add(lbCodigoFactura , 0, 1);
        gpBloqueVenta.add(tfCodigoFactura , 1, 1);
               
        gpBloqueVenta.add(lbFolio , 2, 1);
        gpBloqueVenta.add(tfFolio, 3, 1);       
        HBox hbTotal = new HBox();
        hbTotal.setPrefWidth(400);
        hbTotal.setMaxWidth(500);
        hbTotal.setMinWidth(400);
        hbTotal.setSpacing(10);
        hbTotal.setAlignment(Pos.CENTER_RIGHT);
        hbTotal.getChildren().addAll(lbEtiquetaMonto, lbMontoTotal);
        
        hbCompSeleccion.getChildren().addAll(hbTipoSeleccion, hbTotal);
        hbCompSeleccion.setPadding(new Insets(10, 10, 10, 10));
        hbCompSeleccion.setSpacing(5);

        Separator spSeleccionProductos = new Separator();
        vbHead.getChildren().addAll(hbCompSeleccion, spSeleccionProductos);
        
        VBox vbTabInventario = new VBox();
        vbTabInventario.setPadding(new Insets(5, 5, 5, 5));
        
        vbTabInventario.getChildren().addAll( tvTablaVentas);
        
        Separator spVenta = new Separator();
        Separator spProducto =  new Separator();
        
        Button btnCancelar = new Button("Salir");
        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        
        HBox hbBotonesInferiores = new HBox();
        hbBotonesInferiores.setAlignment(Pos.CENTER_RIGHT);
        hbBotonesInferiores.getChildren().addAll(btnCancelar);
        
        VBox vbTabProducto = new VBox();
        vbTabProducto.setSpacing(5);
        vbTabProducto.getChildren().addAll(vbBuscarVenta, gpClienteSeleccionado, gpBloqueVenta, spProducto, hbBotonesInferiores);
        
        
        HBox hbBody = new HBox();
        hbBody.getChildren().addAll(vbTabInventario, vbTabProducto);
        
        vbVistaPpal.getChildren().addAll(lbTituloVista, vbHead, hbBody);
        
        return vbVistaPpal; 
    }
    private VBox vistaModificarVenta(){
        
         if (detventa.size()>0){
            detventa = FXCollections.observableArrayList();
        }
        
        tfCodigoCliente.setText("");
        tfNombre.setText("");
        tfRazonSocial.setText("");
        tfDomicilioFiscal.setText("");
        tfTelefono.setText("");
        tfRFC.setText("");
        tfEmail.setText(""); 
        tfCodigoCliente.setEditable(false);
        tfNombre.setEditable(false);
        tfRazonSocial.setEditable(false);
        tfDomicilioFiscal.setEditable(false);
        tfTelefono.setEditable(false);
        tfRFC.setEditable(false);
        tfEmail.setEditable(false);
        
        VBox vbVistaPpal = new VBox();

        Label lbTituloVista = new Label("MODIFICAR DETALLES DE VENTA");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        //Etiquetas/datos de la Remisión
        Label lbFolio = new Label("Folio de Nota: ");
        Label lbFecha = new Label("Fecha: ");
        
        TextField tfFolio = new TextField();
         DatePicker dpFecha = new DatePicker(LocalDate.now());
         dpFecha.setDisable(true);


        //Etiquetas/Datos de la Venta
        Label lbEtiquetaMonto = new Label("TOTAL: $ ");
        Label lbMontoTotal = new Label("0.0");
        lbEtiquetaMonto.setFont(fuente);
        lbMontoTotal.setFont(fuente);
        
        //Componentes para busqueda de ventas
        Label lbBuscarPorFecha = new  Label("Fecha:");
        Label lbBuscarPorTipo = new Label("Tipo Venta :");
        DatePicker dpBuscarPorFecha = new DatePicker(LocalDate.now());
        if (usrActivo.getTipo().toString().equals("VENTA")){
            dpBuscarPorFecha.setEditable(false);
            dpBuscarPorFecha.setDisable(true);
        }
        TextField tfBuscarPorTipo = new TextField();
        if (usrActivo.getTipo().toString().equals("VENTA")){
            tfBuscarPorTipo.setDisable(true);
            tfBuscarPorTipo.setEditable(false);
        }        
        Button btnBuscarVenta = new Button("Buscar Ventas");

        
        RadioButton rbBuscarPorFecha = new RadioButton("Buscar por Fecha");
        RadioButton rbBuscarPorTipo = new RadioButton("Buscar por Tipo Venta");
        
        ToggleGroup tgOpcionesBusqueda = new ToggleGroup();
        rbBuscarPorFecha.setToggleGroup(tgOpcionesBusqueda);
        rbBuscarPorTipo.setToggleGroup(tgOpcionesBusqueda);
        rbBuscarPorFecha.setSelected(true);
        
        if (usrActivo.getTipo().toString().equals("VENTA"))rbBuscarPorTipo.setDisable(true);
        
        TableView tvTablaVentas = new TableView();
        
        TableColumn<VENTA, Integer> idVentaColumna = new TableColumn<>("Id Venta");
        idVentaColumna.setMinWidth(120);
        idVentaColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_nota_venta")); 
        
        TableColumn<VENTA, String> FechaVentaColumna = new TableColumn<>("Fecha");
        FechaVentaColumna.setMinWidth(120);
        FechaVentaColumna.setCellValueFactory(new PropertyValueFactory<>("fecha")); 
        
        TableColumn<VENTA, String> tipoVentaColumna = new TableColumn<>("Tipo");
        tipoVentaColumna.setMinWidth(120);
        tipoVentaColumna.setCellValueFactory(new PropertyValueFactory<>("tipo_venta"));
        
        TableColumn<VENTA, Integer> clienteVentaColumna = new TableColumn<>("Codigo Cliente");
        clienteVentaColumna.setMinWidth(120);
        clienteVentaColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_cliente"));        
        
        tvTablaVentas.getColumns().addAll(idVentaColumna, FechaVentaColumna, tipoVentaColumna, clienteVentaColumna);

        MenuItem miEliminarDetVenta = new MenuItem("Eliminar");

        
        ContextMenu cmTabVentas = new ContextMenu();
        cmTabVentas.getItems().add(miEliminarDetVenta);
        
        btnBuscarVenta.setOnAction((event) -> {
            if (!lstventa.isEmpty()){
                lstventa.clear();
            }
            if (rbBuscarPorFecha.isSelected()==true){
                  lstWhereConcepto.clear();
                  lstWhereConcepto.add("fecha = '"+dpBuscarPorFecha.getValue().toString()+"' ");
                  lstventa = FXCollections.observableArrayList(ventDAO.consultaVenta(lstWhereConcepto));
                  tvTablaVentas.setItems(lstventa);
            }
            
            if (rbBuscarPorTipo.isSelected()==true){
                  lstWhereConcepto.clear();
                  lstWhereConcepto.add("tipo_venta = '"+tfBuscarPorTipo.getText()+"' ");
                  lstventa = FXCollections.observableArrayList(ventDAO.consultaVenta(lstWhereConcepto));
                  tvTablaVentas.setItems(lstventa);
            }
        });
        
        GridPane gpCBV = new GridPane();
        gpCBV.setHgap(10);
        gpCBV.setVgap(5);
        gpCBV.add(rbBuscarPorFecha, 0, 0);
        gpCBV.add(rbBuscarPorTipo, 1, 0);
        gpCBV.add(lbBuscarPorFecha, 0, 1);
        gpCBV.add(dpBuscarPorFecha, 1, 1);
        gpCBV.add(lbBuscarPorTipo, 2, 1);
        gpCBV.add(tfBuscarPorTipo, 3, 1);
        gpCBV.add(btnBuscarVenta, 4, 1);
        
        Pane tvPane = new Pane();
        
        VBox vbBuscarVenta = new VBox();
        vbBuscarVenta.setSpacing(5);
        vbBuscarVenta.getChildren().addAll(gpCBV, tvTablaVentas);
        
	Label lbTipo_venta = new Label("Tipo de Venta: ");
        Label lbCodigoFactura = new Label("Codigo Factura");
        
	Label lbCantidad  = new Label("Cantidad");
	Label lbCodigoProd  = new Label("Codigo Producto: ");
        Label lbDescProducto = new Label ("Descripción Producto: ");
	Label lbPrecioMenudeo  = new Label("Precio Menudeo: ");
	Label lbPrecioMayoreo  = new Label("Precrio Mayoreo: ");
        Label lbPrecioVenta = new Label("Precio Venta");
        
        ObservableList<String> lstOpcionesTipoVenta = FXCollections.observableArrayList("EFECTIVO","VENTA A CREDITO", "APARTADO", "TARJETA", "TRANSFERENCIA");
        ComboBox cbTipoVenta = new ComboBox(lstOpcionesTipoVenta);
        cbTipoVenta.setEditable(false);
        cbTipoVenta.setDisable(true);
        cbTipoVenta.setPrefWidth(180);
        
        TextField tfCantidad = new TextField();
        tfCantidad.setMaxWidth(80);
        TextField tfCodigoFactura = new TextField();
        tfCodigoFactura.setEditable(false);
        
        TextField tfCodigoProducto = new TextField();
        tfCodigoProducto.setEditable(false);
        TextField tfDescrProd = new TextField();
        tfDescrProd.setEditable(false);
        tfDescrProd.setMaxWidth(300);
        tfDescrProd.setPrefWidth(300);
        
        TextField tfPrecioMayor = new TextField();
        tfPrecioMayor.setMaxWidth(120);
        tfPrecioMayor.setPrefWidth(120);
        tfPrecioMayor.setEditable(false);
        TextField tfPrecioMenudeo = new TextField();
        tfPrecioMenudeo.setEditable(false);
        TextField tfPrecioVenta = new TextField();
        
        
        //Etiquetas/Datos de Cliente;
        Label lbCodigo_cliente  = new Label("Codigo_Cliente");
	Label lbNombre  = new Label("Nombre: ");
	Label lbRazon_social  = new Label("Razon Social");
	Label lbDomicilio_fiscal  = new Label("Dom. Fiscal");
	Label lbTelefono  = new Label("Telefono.");
	Label lbRFC  = new Label("RFC");
	Label lbEmail  = new Label("Email:");
        
        //Componentes de Interfaz
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgBusquedas = new ToggleGroup();
        
        RadioButton rbTodos = new RadioButton("Todos");
        RadioButton rbCodigo = new RadioButton("Codigo");
        RadioButton rbDescripcion = new RadioButton("Descripción");
        RadioButton rbCategoria = new RadioButton("Categoria");
        
        rbTodos.setSelected(true);
        
        rbTodos.setToggleGroup(tgBusquedas);
        rbCodigo.setToggleGroup(tgBusquedas);
        rbDescripcion.setToggleGroup(tgBusquedas);
        rbCategoria.setToggleGroup(tgBusquedas);
        
        ToggleGroup tgPrecio = new ToggleGroup();
        RadioButton rbPrecioMenudeo =  new RadioButton("Precio Menudeo");
        rbPrecioMenudeo.setOnAction((ActionEvent e)->{
            tfPrecioVenta.setText(tfPrecioMenudeo.getText());
        });
        
        RadioButton rbPrecioMayoreo = new RadioButton("Precio Mayoreo");
        rbPrecioMayoreo.setOnAction((ActionEvent e)->{
            tfPrecioVenta.setText(tfPrecioMayor.getText());
        });
                
        rbPrecioMenudeo.setToggleGroup(tgPrecio);
        rbPrecioMayoreo.setToggleGroup(tgPrecio);
        
       List<String> lstCategorias = new ArrayList<>();
       List<String> lstWherecat = new ArrayList<>();
       lstWherecat.add("id_categoria is not null");
       for (categoria i : categDAO.consultarCategoria(lstWherecat)){
            lstCategorias.add(i.getCategoria());
       }
        
        Label lbCodigo = new Label("Codigo: ");
        TextField tfCodigo = new TextField();
        Label lbDescripcion = new Label("Descripción: ");
        TextField tfDescripcion = new TextField();
        Label lbCategoria = new Label("Categoria: ");
        ComboBox cbCategoria = new ComboBox(FXCollections.observableArrayList(lstCategorias));
        cbCategoria.setPrefWidth(140);
        
        Label lbInventario = new Label("Tabla Inventario: ");
        TableView tvInventario = new TableView();
        tvInventario.setPrefHeight(350);
        tvInventario.setPrefWidth(550);
        
        TableColumn<inventario, Integer> claveProdColumna = new TableColumn<>("Codigo Producto");
        claveProdColumna.setMinWidth(120);
        claveProdColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));

        TableColumn<inventario, Integer> existenciaColumna = new TableColumn<>("Existencia");
        existenciaColumna.setMinWidth(120);
        existenciaColumna.setCellValueFactory(new PropertyValueFactory<>("existencia"));
        
        TableColumn<inventario, Integer> idUbicacionColumna = new TableColumn<>("Id Ubicación");
        idUbicacionColumna.setMinWidth(120);
        idUbicacionColumna.setCellValueFactory(new PropertyValueFactory<>("id_ubicacion"));
        
        TableColumn<inventario, Float> pMenudeoColumna = new TableColumn<>("Precio Menudeo");
        pMenudeoColumna.setMinWidth(120);
        pMenudeoColumna.setCellValueFactory(new PropertyValueFactory<>("precio_menudeo"));        

        TableColumn<inventario, Float> pMayoreoColumna = new TableColumn<>("Precio Mayoreo");
        pMayoreoColumna.setMinWidth(120);
        pMayoreoColumna.setCellValueFactory(new PropertyValueFactory<>("precio_mayoreo"));
        
        TableColumn<inventario, String> descripcionColumna = new TableColumn<>("Descripción");
        descripcionColumna.setMinWidth(120);
        descripcionColumna.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        
        TableColumn<inventario, String> uMedidaColumna = new TableColumn<>("Unidad Medidad");
        uMedidaColumna.setMinWidth(120);
        uMedidaColumna.setCellValueFactory(new PropertyValueFactory<>("unidad_medida"));

        TableColumn<inventario, Float> cCompraColumna = new TableColumn<>("Costo Compra");
        cCompraColumna.setMinWidth(120);
        cCompraColumna.setCellValueFactory(new PropertyValueFactory<>("costo_compra"));
        
        TableColumn<inventario, Integer> codProvColumna = new TableColumn<>("Codigo Proveedor");
        codProvColumna.setMinWidth(120);
        codProvColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prov"));
        
        tvInventario.getColumns().addAll(claveProdColumna, descripcionColumna, existenciaColumna,
                pMenudeoColumna, pMayoreoColumna, uMedidaColumna, idUbicacionColumna, 
                codProvColumna, cCompraColumna);
        List<String> lstWhere = new ArrayList<>();
        lstWhere.add("codigo_prod is not null");
        tvInventario.setItems(invent.consultarInventario(lstWhere));
        
        tvInventario.setOnMouseClicked((event) -> {
            inventario inv = new inventario();
            inv = (inventario) tvInventario.getSelectionModel().getSelectedItem();
            tfCodigoProducto.setText(String.valueOf(inv.getCodigo_prod()));
            tfDescrProd.setText(inv.getDescripcion());
            tfPrecioMenudeo.setText(String.valueOf(inv.getPrecio_menudeo()));
            tfPrecioMayor.setText(String.valueOf(inv.getPrecio_mayoreo()));
            rbPrecioMenudeo.setSelected(true);
            tfPrecioVenta.setText(tfPrecioMenudeo.getText());
        });
        
        
        Label lbProducto = new Label("Tabla Producto Seleccionados: ");
        TableView tvProductosSelecc = new TableView();
        tvProductosSelecc.setPrefHeight(350);
        tvProductosSelecc.setPrefWidth(550);
        tvProductosSelecc.setContextMenu(cmTabVentas);
        //id_detalle_venta clave_prod cantidad codigo_nota_venta 
        //codigo_prod precio_menudeo precio_mayoreo descrprod 
        
        TableColumn<detalle_venta, Integer> idDetalleVentaColumna = new TableColumn<>("Id Detalle Venta");
        idDetalleVentaColumna.setMinWidth(120);
        idDetalleVentaColumna.setCellValueFactory(new PropertyValueFactory<>("id_detalle_venta"));
        
        TableColumn<detalle_venta, Integer> codigoProductColumna = new TableColumn<>("Codigo Producto");
        codigoProductColumna.setMinWidth(80);
        codigoProductColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));
        
        TableColumn<detalle_venta, String> descrProductColumna = new TableColumn<>("Descripción Producto");
        descrProductColumna.setMinWidth(220);
        descrProductColumna.setCellValueFactory(new PropertyValueFactory<>("descrprod"));
        
        TableColumn<detalle_venta, Integer> cantidadProductColumna = new TableColumn<>("Cantidad");
        cantidadProductColumna.setMinWidth(80);
        cantidadProductColumna.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        
        TableColumn<detalle_venta, Integer> codigoNotaVentaColumna = new TableColumn<>("Codigo Nota Venta");
        codigoNotaVentaColumna.setMinWidth(120);
        codigoNotaVentaColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_nota_venta"));

        TableColumn<detalle_venta, Integer> precioVentaColumna = new TableColumn<>("Precio Venta");
        precioVentaColumna.setMinWidth(120);
        precioVentaColumna.setCellValueFactory(new PropertyValueFactory<>("precio_venta"));
        
        TableColumn<detalle_venta, Integer> subtotalColumna = new TableColumn<>("Sub-total");
        subtotalColumna.setMinWidth(120);
        subtotalColumna.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
        
        tvProductosSelecc.getColumns().addAll(codigoProductColumna, descrProductColumna, cantidadProductColumna, precioVentaColumna, subtotalColumna);
        tvProductosSelecc.setItems(detventa);
        
        miEliminarDetVenta.setOnAction((event) -> {
            detalle_venta dtvtaTemp = (detalle_venta) tvProductosSelecc.getSelectionModel().getSelectedItem();
            lstWhere.clear();
            lstWhere.add("codigo_prod = "+ dtvtaTemp.getCodigo_prod());
            inventario iv =  invent.consultaInventario(lstWhere).get(0);
            int nvaCantidad = iv.getExistencia()+dtvtaTemp.getCantidad();
            invent.modificarExistenciaProducto(dtvtaTemp.getCodigo_prod(), nvaCantidad);
            detventaDAO.borrarDetVenta(dtvtaTemp.getId_detalle_venta());
            detventa.clear();
            lstWhereConcepto.clear();
            lstWhereConcepto.add("codigo_nota_venta = "+dtvtaTemp.getCodigo_nota_venta());
            detventa = FXCollections.observableArrayList(detventaDAO.consultaDetVenta(lstWhereConcepto));
            lbMontoTotal.setText("0.0");
            for (detalle_venta detv : detventa){
                lstWhere.clear();
                lstWhere.add("codigo_prod = "+detv.getCodigo_prod());
                inventario ivTemp = invent.consultaInventario(lstWhere).get(0);
                detv.setExistencia(ivTemp.getExistencia());
                detv.setSubTotal(detv.getCantidad()* detv.getPrecio_venta());
                float MontoTotal = Float.parseFloat(lbMontoTotal.getText());
                MontoTotal = MontoTotal + detv.getSubTotal();
                lbMontoTotal.setText(Float.toString(MontoTotal));               
            }
            tvProductosSelecc.setItems(detventa);
            
        });

        Button btnAgregarProducto = new Button("Agregar Producto");
        btnAgregarProducto.setOnAction((ActionEvent e)->{
            inventario inv = new inventario();
            inv = (inventario) tvInventario.getSelectionModel().getSelectedItem();
            if (tfCodigoProducto.getText().length()>0){
             if (tfCantidad.getText().length()>0){
                 if (inv.getExistencia()> Integer.parseInt(tfCantidad.getText())){
                     if (tfPrecioVenta.getText().length()> 0){
                         detalle_venta detvta = new detalle_venta();
                         detvta.setCodigo_prod(Integer.parseInt(tfCodigoProducto.getText()));
                         detvta.setDescrprod(tfDescrProd.getText());
                         detvta.setCantidad(Integer.parseInt(tfCantidad.getText()));
                         detvta.setExistencia(inv.getExistencia());
                         detvta.setPrecio_venta(Float.parseFloat(tfPrecioVenta.getText()));
                         detvta.setSubTotal(Integer.parseInt(tfCantidad.getText())* Float.parseFloat(tfPrecioVenta.getText()));
                         float MontoTotal = Float.parseFloat(lbMontoTotal.getText());
                         MontoTotal = MontoTotal + Integer.parseInt(tfCantidad.getText())* Float.parseFloat(tfPrecioVenta.getText());
                         lbMontoTotal.setText(Float.toString(MontoTotal));
                         detventa.add(detvta);
                         System.out.println("Cuantos van:"+detventa.size());
                        }else {
                         Alert MensajeError = new Alert(Alert.AlertType.ERROR);
                         MensajeError.setTitle("Error");
                         MensajeError.setContentText("Falta Costo de Venta");
                         MensajeError.show();
                        }
                    }else {
                       Alert MensajeError = new Alert(Alert.AlertType.ERROR);
                       MensajeError.setTitle("Error");
                       MensajeError.setContentText("No puedes poner una cantidad mayor a la de la existencia en inventario");
                       MensajeError.show();
                    }
                }else {
               Alert MensajeError = new Alert(Alert.AlertType.ERROR);
               MensajeError.setTitle("Error");
               MensajeError.setContentText("Falta Cantidad de compra");
               MensajeError.show();
              }
            } else{
               Alert MensajeError = new Alert(Alert.AlertType.ERROR);
               MensajeError.setTitle("Error");
               MensajeError.setContentText("Falta Seleccionar Producto");
               MensajeError.show();           
            }
        });
        
        tvTablaVentas.setOnMouseClicked((event) -> {
            
            VENTA vtaTemp = (VENTA) tvTablaVentas.getSelectionModel().getSelectedItem();
            lstWhereConcepto.clear();
            lstWhereConcepto.add("codigo_cliente = "+vtaTemp.getCodigo_cliente());
            CLIENTE cliIden = cliDAO.consultarClientes(lstWhereConcepto).get(0);
            tfCodigoCliente.setText(String.valueOf(cliIden.getCodigo_cliente()));
            tfNombre.setText(cliIden.getNombre());
            tfRazonSocial.setText(cliIden.getRazon_social());
            tfDomicilioFiscal.setText(cliIden.getDomicilio_fiscal());
            tfEmail.setText(cliIden.getEmail());
            tfTelefono.setText(cliIden.getTelefono());
            tfRFC.setText(cliIden.getRfc());
            cbTipoVenta.setValue(vtaTemp.getTipo_venta());
            dpFecha.setValue(LocalDate.parse(vtaTemp.getFecha()));
            tfCodigoFactura.setText(vtaTemp.getCodigo_factura());
            lstWhereConcepto.clear();
            lstWhereConcepto.add("id_nota_rem = "+vtaTemp.getId_nota_rem());
            notas_remision notasRemTemp = notasDAO.consultarNotasRem(lstWhereConcepto).get(0);
            tfFolio.setText(String.valueOf(notasRemTemp.getFolio()));
            if(!detventa.isEmpty()){
               detventa.clear();
            }
            lstWhereConcepto.clear();
            lstWhereConcepto.add("codigo_nota_venta = "+vtaTemp.getCodigo_nota_venta());
            detventa = FXCollections.observableArrayList(detventaDAO.consultaDetVenta(lstWhereConcepto));
            lbMontoTotal.setText("0.0");
            for (detalle_venta detv : detventa){
                lstWhere.clear();
                lstWhere.add("codigo_prod = "+detv.getCodigo_prod());
                inventario ivTemp = invent.consultaInventario(lstWhere).get(0);
                detv.setExistencia(ivTemp.getExistencia());
                detv.setSubTotal(detv.getCantidad()* detv.getPrecio_venta());
                float MontoTotal = Float.parseFloat(lbMontoTotal.getText());
                MontoTotal = MontoTotal + detv.getSubTotal();
                lbMontoTotal.setText(Float.toString(MontoTotal));               
            }
            tvProductosSelecc.setItems(detventa);
        });        
        
        GridPane gpClienteSeleccionado = new GridPane();
        gpClienteSeleccionado.setPadding(new Insets(5, 5, 5, 5));
        gpClienteSeleccionado.setVgap(10);
        gpClienteSeleccionado.setHgap(10);
        
        gpClienteSeleccionado.add(lbCodigo_cliente, 0, 0);
        gpClienteSeleccionado.add(tfCodigoCliente, 1, 0);
        gpClienteSeleccionado.add(lbNombre, 2, 0);
        gpClienteSeleccionado.add(tfNombre, 3, 0);
        
        gpClienteSeleccionado.add(lbRazon_social, 0, 1);
        gpClienteSeleccionado.add(tfRazonSocial, 1, 1);
        gpClienteSeleccionado.add(lbDomicilio_fiscal, 2, 1);
        gpClienteSeleccionado.add(tfDomicilioFiscal, 3, 1);
        
        gpClienteSeleccionado.add(lbTelefono, 0, 2);
        gpClienteSeleccionado.add(tfTelefono, 1, 2);
        gpClienteSeleccionado.add(lbRFC, 2, 2);
        gpClienteSeleccionado.add(tfRFC, 3, 2);
        
        gpClienteSeleccionado.add(lbEmail, 0, 3);
        gpClienteSeleccionado.add(tfEmail, 1, 3);
        
        VBox vbHead = new VBox();

        /*VBox vbClienteSeleccion = new VBox();
        vbClienteSeleccion.setSpacing(10);
        vbClienteSeleccion.getChildren().addAll(gpClienteSeleccionado);*/
        
        HBox hbTipoSeleccion = new HBox();
        hbTipoSeleccion.getChildren().addAll(rbTodos, rbCodigo, rbDescripcion, rbCategoria);
        hbTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        hbTipoSeleccion.setSpacing(5);
        
        VBox vbCodigo = new VBox();
        vbCodigo.getChildren().addAll(lbCodigo, tfCodigo);
        vbCodigo.setSpacing(5);
        
        VBox vbDesc = new VBox();
        vbDesc.getChildren().addAll(lbDescripcion, tfDescripcion);
        vbDesc.setSpacing(5);
        
        VBox vbCat = new VBox();
        vbCat.getChildren().addAll(lbCategoria, cbCategoria);
        vbCat.setSpacing(5);
        
        HBox hbCompSeleccion = new HBox();
        Button btnBuscarProductos = new Button("Seleccionar");
        btnBuscarProductos.setMaxHeight(50);
        btnBuscarProductos.setOnAction((ActionEvent e)->{
         List<inventario> lstInv = new ArrayList<>();
         List<String> lstWherelc = new ArrayList<>();
         
          if (rbTodos.isSelected()){
           lstWherelc.add("codigo_prod is not null");
           tvInventario.setItems(invent.consultarInventario(lstWherelc));
           //String titulo = "MODIFICAR PRODUCTOS ("+String.valueOf(tvInventario.getItems().size())+" Seleccionados)";
         } 
         
         if (rbCodigo.isSelected()){
           lstWhere.add("codigo_prod = "+tfCodigo.getText());
           //lstInv=invent.consultarInventario(lstWhere);
           //ObservableList obList = FXCollections.observableArrayList(lstInv);
           tvInventario.setItems(invent.consultarInventario(lstWhere));
         }
         if (rbDescripcion.isSelected()){
             
           lstWherelc.add("descripcion like '"+tfDescripcion.getText()+"%'");
           //lstInv=invent.consultarInventario(lstWhere);
           //ObservableList obList = FXCollections.observableArrayList(lstInv);
           tvInventario.setItems(invent.consultarInventario(lstWherelc));
         }
         
         if (rbCategoria.isSelected()){
           lstWherecat.add("id_categoria is not null");  
           for (categoria i : categDAO.consultarCategoria(lstWherecat)){
             String strCategoria =  i.getCategoria();
             if (strCategoria.compareTo(cbCategoria.getSelectionModel().getSelectedItem().toString())==0){
                  lstWhere.add("id_categoria = "+i.getId_categoria());
                  tvInventario.setItems(invent.consultarInventario(lstWhere));
             }
           }
         }
        });
        
        HBox hbTotal = new HBox();
        hbTotal.setPrefWidth(400);
        hbTotal.setMaxWidth(500);
        hbTotal.setMinWidth(400);
        hbTotal.setSpacing(10);
        hbTotal.setAlignment(Pos.CENTER_RIGHT);
        hbTotal.getChildren().addAll(lbEtiquetaMonto, lbMontoTotal);
        
        hbCompSeleccion.getChildren().addAll(vbCodigo, vbDesc, vbCat, btnBuscarProductos, hbTotal);
        hbCompSeleccion.setPadding(new Insets(10, 10, 10, 10));
        hbCompSeleccion.setSpacing(5);

        Separator spSeleccionProductos = new Separator();
        vbHead.getChildren().addAll(lbTipoBusqueda, hbTipoSeleccion, hbCompSeleccion, spSeleccionProductos);
        
        VBox vbTabInventario = new VBox();
        vbTabInventario.setPadding(new Insets(5, 5, 5, 5));
        
        
        vbTabInventario.getChildren().addAll(lbInventario, tvInventario, lbProducto, tvProductosSelecc);
        
        Separator spVenta = new Separator();
        Separator spProducto =  new Separator();
        
        GridPane gpBloqueVenta = new GridPane();
        gpBloqueVenta.setPadding(new Insets(5, 5, 5, 5));
        gpBloqueVenta.setVgap(10);
        gpBloqueVenta.setHgap(10);
        
        gpBloqueVenta.add(lbTipo_venta, 0, 0);
        gpBloqueVenta.add(cbTipoVenta , 1, 0);

        gpBloqueVenta.add(lbFecha , 2, 0);
        gpBloqueVenta.add(dpFecha , 3, 0);

        
        gpBloqueVenta.add(lbCodigoFactura , 0, 1);
        gpBloqueVenta.add(tfCodigoFactura , 1, 1);
               
        gpBloqueVenta.add(lbFolio , 2, 1);
        gpBloqueVenta.add(tfFolio, 3, 1);

        GridPane gpBloqueProducto = new GridPane();
        gpBloqueProducto.setPadding(new Insets(5, 5, 5, 5));
        gpBloqueProducto.setVgap(10);
        gpBloqueProducto.setHgap(10);
        
        gpBloqueProducto.add(lbCodigoProd , 0, 0);
        gpBloqueProducto.add(tfCodigoProducto , 1, 0);
        
        gpBloqueProducto.add(lbCantidad , 2, 0);
        gpBloqueProducto.add(tfCantidad , 3, 0);
        
        gpBloqueProducto.add(lbDescProducto , 2, 1);
        gpBloqueProducto.add(tfDescrProd , 3, 1);
        
        gpBloqueProducto.add(rbPrecioMenudeo, 0, 2);
        gpBloqueProducto.add(rbPrecioMayoreo, 2, 2);

        gpBloqueProducto.add(lbPrecioMenudeo , 0, 3);
        gpBloqueProducto.add(tfPrecioMenudeo , 1, 3);

        gpBloqueProducto.add(lbPrecioMayoreo , 2, 3);
        gpBloqueProducto.add(tfPrecioMayor , 3, 3);
        
        gpBloqueProducto.add(lbPrecioVenta, 0, 4);
        gpBloqueProducto.add(tfPrecioVenta, 1, 4);
        
        gpBloqueProducto.add(btnAgregarProducto, 2, 4);

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                  tfCodigoCliente.setText("");
                  tfNombre.setText("");
                  tfRazonSocial.setText("");
                  tfDomicilioFiscal.setText("");
                  tfTelefono.setText("");
                  tfRFC.setText("");
                  tfEmail.setText(""); 
                  tfCodigoCliente.setEditable(true);
                  tfNombre.setEditable(true);
                  tfRazonSocial.setEditable(true);
                  tfDomicilioFiscal.setEditable(true);
                  tfTelefono.setEditable(true);
                  tfRFC.setEditable(true);
                  tfEmail.setEditable(true);
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        
        Button btnGuardar = new Button("Modificar Venta");
        btnGuardar.setOnAction((event) -> {
           if (tfCodigoCliente.getText().length()>0){
               if (!detventa.isEmpty()){
                   for (detalle_venta dtvTemp : detventa){
                       detventaDAO.borrarDetVenta(dtvTemp.getId_detalle_venta());
                   }
                   String valueTipoVenta = cbTipoVenta.getValue().toString();
                   if (valueTipoVenta.length()>0){
                       //System.out.println("Longitud Tipo Venta -->"+ cbTipoVenta.getValue().toString().length());
                       if (tfFolio.getText().length()>0){
                           VENTA vta = (VENTA) tvTablaVentas.getSelectionModel().getSelectedItem();
                   
                           notas_remision notaRem = new notas_remision();
                           notaRem.setId_nota_rem(vta.getId_nota_rem());
                           notaRem.setBandera(1);
                           notaRem.setFecha(dpFecha.getValue().toString());
                           notaRem.setFolio(tfFolio.getText());
                           notaRem.setMonto(Float.parseFloat(lbMontoTotal.getText()));
                           notaRem.setTipo_operacion(cbTipoVenta.getValue().toString());
                           /*if (!tfCodigoFactura.getText().isEmpty() && cbTipoVenta.getValue().toString().compareTo("REMISION EFECTIVO")==0 )
                             notaRem.setTipo_operacion(cbTipoVenta.getValue().toString());
                           else if (!tfCodigoFactura.getText().isEmpty() && cbTipoVenta.getValue().toString().compareTo("SISTEMA EFECTIVO")==0 )
                             notaRem.setTipo_operacion(cbTipoVenta.getValue().toString());
                           else notaRem.setTipo_operacion(cbTipoVenta.getValue().toString());*/
            
                           Credito cred = new Credito();
                           cred.setBandera(1);
                           cred.setCodigo_cliente(Integer.parseInt(tfCodigoCliente.getText()));
                           cred.setMonto(Float.parseFloat(lbMontoTotal.getText()));
                           cred.setFecha(dpFecha.getValue().toString());
            
                           apartado apart = new apartado();
                           apart.setBandera(1);
                           apart.setCodigo_cliente(Integer.parseInt(tfCodigoCliente.getText()));
                           apart.setMonto(Float.parseFloat(lbMontoTotal.getText()));
                           System.out.println("Monto Apartado: "+ apart.getMonto());
                           apart.setFecha(dpFecha.getValue().toString());
            
                           VentaBO ventBo = new VentaBO();
                           String strTipoVenta = cbTipoVenta.getSelectionModel().getSelectedItem().toString();
                           if (strTipoVenta.contains("EFECTIVO")){
                                ventBo.modificarVenta(vta.getCodigo_nota_venta(), notaRem, cred, apart, detventa, 0);//Venta(vta, notaRem, cred, apart, detventa, 0);
                                Alert altMensaje = new Alert(Alert.AlertType.INFORMATION);
                                altMensaje.setContentText("Venta en Efectivo Registrada");
                                altMensaje.setTitle("Informacion-Venta");
                                altMensaje.show();
                            } // "Transferencia"
                           if (strTipoVenta.compareTo("VENTA A CREDITO")==0){
                                   ventBo.modificarVenta(vta.getCodigo_nota_venta(), notaRem, cred, apart, detventa, 1);
                                   Alert altMensaje = new Alert(Alert.AlertType.INFORMATION);
                                   altMensaje.setContentText("Venta a Credito Registrada");
                                   altMensaje.setTitle("Informacion-Venta");
                                   altMensaje.show();
                            }            
                            if (strTipoVenta.compareTo("APARTADO")==0){
                                  ventBo.modificarVenta(vta.getCodigo_nota_venta(), notaRem, cred, apart, detventa, 2);
                                  Alert altMensaje =new Alert(Alert.AlertType.INFORMATION);
                                  altMensaje.setContentText("Venta en Apartado Registrada");
                                  altMensaje.setTitle("Informacion-Venta");
                                  altMensaje.show();
                            } 
                            if (strTipoVenta.compareTo("TARJETA")==0){
                                  ventBo.modificarVenta(vta.getCodigo_nota_venta(), notaRem, cred, apart, detventa, 3);
                                  Alert altMensaje =new Alert(Alert.AlertType.INFORMATION);
                                  altMensaje.setContentText("Venta Con Tarjeta de credito Registrada");
                                  altMensaje.setTitle("Informacion-Venta");
                                  altMensaje.show();
                            } 
                            if (strTipoVenta.compareTo("TRANSFERENCIA")==0){
                                   ventBo.modificarVenta(vta.getCodigo_nota_venta(), notaRem, cred, apart, detventa, 4);
                                   Alert altMensaje =new Alert(Alert.AlertType.INFORMATION);
                                   altMensaje.setContentText("Venta con Transferencia Registrada");
                                   altMensaje.setTitle("Informacion-Venta");
                                   altMensaje.show();
                            }
                             tfCodigoCliente.setText("");
                             tfNombre.setText("");
                             tfRazonSocial.setText("");
                             tfDomicilioFiscal.setText("");
                             tfTelefono.setText("");
                             tfRFC.setText("");
                             tfEmail.setText(""); 
                             tfCodigoCliente.setEditable(true);
                             tfNombre.setEditable(true);
                             tfRazonSocial.setEditable(true);
                             tfDomicilioFiscal.setEditable(true);
                             tfTelefono.setEditable(true);
                             tfRFC.setEditable(true);
                             tfEmail.setEditable(true);
                            removerVistas();
                       }else{
                        Alert MensajeError = new Alert(Alert.AlertType.ERROR);
                        MensajeError.setTitle("Error");
                        MensajeError.setContentText("Falta Folio de Remision");
                        MensajeError.show();             
                       }
                    }else{
                        Alert MensajeError = new Alert(Alert.AlertType.ERROR);
                        MensajeError.setTitle("Error");
                        MensajeError.setContentText("Falta Tipo de Venta");
                        MensajeError.show();             
                    }
                }else{
                        Alert MensajeError = new Alert(Alert.AlertType.ERROR);
                        MensajeError.setTitle("Error");
                        MensajeError.setContentText("Falta Seleccionar Producto");
                        MensajeError.show();             
                }
            }else {
                    Alert MensajeError = new Alert(Alert.AlertType.ERROR);
                    MensajeError.setTitle("Error");
                    MensajeError.setContentText("Falta Seleccionar Cliente");
                    MensajeError.show();
            }
        });
        
        HBox hbBotonesInferiores = new HBox();
        hbBotonesInferiores.setAlignment(Pos.CENTER_RIGHT);
        hbBotonesInferiores.getChildren().addAll(btnCancelar, btnGuardar);
        
        VBox vbTabProducto = new VBox();
        vbTabProducto.setSpacing(5);
        vbTabProducto.getChildren().addAll(vbBuscarVenta, gpClienteSeleccionado, spVenta, gpBloqueVenta, spProducto, gpBloqueProducto, hbBotonesInferiores);
        
        
        HBox hbBody = new HBox();
        hbBody.getChildren().addAll(vbTabInventario, vbTabProducto);
        
        vbVistaPpal.getChildren().addAll(lbTituloVista, vbHead, hbBody);
        
        return vbVistaPpal; 
    }
    private VBox vistaEliminarVenta(){
        
         if (detventa.size()>0){
            detventa = FXCollections.observableArrayList();
        }
        
        VBox vbVistaPpal = new VBox();
        
        //Etiquetas/Datos de Cliente;
        Label lbCodigo_cliente  = new Label("Codigo_Cliente");
	Label lbNombre  = new Label("Nombre: ");
	Label lbRazon_social  = new Label("Razon Social");
	Label lbDomicilio_fiscal  = new Label("Dom. Fiscal");
	Label lbTelefono  = new Label("Telefono.");
	Label lbRFC  = new Label("RFC");
	Label lbEmail  = new Label("Email:");
	Label lbTipo_venta = new Label("Tipo de Venta: ");
        Label lbCodigoFactura = new Label("Codigo Factura");
        
        Label lbTituloVista = new Label("ELIMINAR VENTA");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        //Etiquetas/datos de la Remisión
        Label lbFolio = new Label("Folio de Nota: ");
        Label lbFecha = new Label("Fecha: ");
        
        TextField tfFolio = new TextField();
        TextField tfCodigoFactura = new TextField();
        DatePicker dpFecha = new DatePicker(LocalDate.now());
        ObservableList<String> lstOpcionesTipoVenta = FXCollections.observableArrayList("EFECTIVO","VENTA A CREDITO", "APARTADO", "TARJETA", "TRANSFERENCIA");
        ComboBox cbTipoVenta = new ComboBox(lstOpcionesTipoVenta);
        cbTipoVenta.setPrefWidth(180);
        
        //Etiquetas/Datos de la Venta
        Label lbEtiquetaMonto = new Label("TOTAL: $ ");
        Label lbMontoTotal = new Label("0.0");
        lbEtiquetaMonto.setFont(fuente);
        lbMontoTotal.setFont(fuente);
        
        //Componentes para busqueda de ventas
        Label lbBuscarPorFecha = new  Label("Fecha:");
        Label lbBuscarPorTipo = new Label("Tipo Venta :");
        DatePicker dpBuscarPorFecha = new DatePicker(LocalDate.now());
        if (usrActivo.getTipo().toString().equals("VENTA")){
            dpBuscarPorFecha.setEditable(false);
            dpBuscarPorFecha.setDisable(true);
        }
        TextField tfBuscarPorTipo = new TextField();
        if (usrActivo.getTipo().toString().equals("VENTA")){
            tfBuscarPorTipo.setDisable(true);
            tfBuscarPorTipo.setEditable(false);
        }        
        
        Button btnBuscarVenta = new Button("Buscar Ventas");
        
        RadioButton rbBuscarPorFecha = new RadioButton("Buscar por Fecha");
        RadioButton rbBuscarPorTipo = new RadioButton("Buscar por Tipo Venta");
        
        ToggleGroup tgOpcionesBusqueda = new ToggleGroup();
        rbBuscarPorFecha.setToggleGroup(tgOpcionesBusqueda);
        rbBuscarPorTipo.setToggleGroup(tgOpcionesBusqueda);
        rbBuscarPorFecha.setSelected(true);
        
        if (usrActivo.getTipo().toString().equals("VENTA"))rbBuscarPorTipo.setDisable(true);
        
        tfCodigoCliente.setText("");
        tfNombre.setText("");
        tfRazonSocial.setText("");
        tfDomicilioFiscal.setText("");
        tfTelefono.setText("");
        tfRFC.setText("");
        tfEmail.setText(""); 
        tfCodigoCliente.setEditable(false);
        tfNombre.setEditable(false);
        tfRazonSocial.setEditable(false);
        tfDomicilioFiscal.setEditable(false);
        tfTelefono.setEditable(false);
        tfRFC.setEditable(false);
        tfEmail.setEditable(false);

        
        TableView tvTablaVentas = new TableView();
        
        TableColumn<VENTA, Integer> idVentaColumna = new TableColumn<>("Id Venta");
        idVentaColumna.setMinWidth(120);
        idVentaColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_nota_venta")); 
        
        TableColumn<VENTA, String> FechaVentaColumna = new TableColumn<>("Fecha");
        FechaVentaColumna.setMinWidth(120);
        FechaVentaColumna.setCellValueFactory(new PropertyValueFactory<>("fecha")); 
        
        TableColumn<VENTA, String> tipoVentaColumna = new TableColumn<>("Tipo");
        tipoVentaColumna.setMinWidth(120);
        tipoVentaColumna.setCellValueFactory(new PropertyValueFactory<>("tipo_venta"));
        
        TableColumn<VENTA, Integer> clienteVentaColumna = new TableColumn<>("Codigo Cliente");
        clienteVentaColumna.setMinWidth(120);
        clienteVentaColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_cliente"));        
        
        tvTablaVentas.getColumns().addAll(idVentaColumna, FechaVentaColumna, tipoVentaColumna, clienteVentaColumna);

        MenuItem miEliminarDetVenta = new MenuItem("Eliminar");
        
        ContextMenu cmTabVentas = new ContextMenu();
        cmTabVentas.getItems().add(miEliminarDetVenta);
        
        btnBuscarVenta.setOnAction((event) -> {
            lbMontoTotal.setText(" 0.0 ");
            tfCodigoCliente.setText("");
            tfDomicilioFiscal.setText("");
            tfEmail.setText("");
            tfNombre.setText("");
            tfRFC.setText("");
            tfRazonSocial.setText("");
            tfTelefono.setText("");
            tfFolio.setText("");
            tfCodigoFactura.setText("");
            cbTipoVenta.setValue("");
            dpFecha.setValue(LocalDate.now());
            //dpFecha = new DatePicker(LocalDate.now());;
            
            if (!lstventa.isEmpty()){
                lstventa.clear();
            }
            if (!detventa.isEmpty()){
               detventa.clear();
            }
            if (rbBuscarPorFecha.isSelected()==true){
                  lstWhereConcepto.clear();
                  lstWhereConcepto.add("fecha = '"+dpBuscarPorFecha.getValue().toString()+"' ");
                  lstventa = FXCollections.observableArrayList(ventDAO.consultaVenta(lstWhereConcepto));
                  tvTablaVentas.setItems(lstventa);
            }
            
            if (rbBuscarPorTipo.isSelected()==true){
                  lstWhereConcepto.clear();
                  lstWhereConcepto.add("tipo_venta = '"+tfBuscarPorTipo.getText()+"' ");
                  lstventa = FXCollections.observableArrayList(ventDAO.consultaVenta(lstWhereConcepto));
                  tvTablaVentas.setItems(lstventa);
            }
        });
        
        GridPane gpCBV = new GridPane();
        gpCBV.setHgap(10);
        gpCBV.setVgap(5);
        gpCBV.add(rbBuscarPorFecha, 0, 0);
        gpCBV.add(rbBuscarPorTipo, 1, 0);
        gpCBV.add(lbBuscarPorFecha, 0, 1);
        gpCBV.add(dpBuscarPorFecha, 1, 1);
        gpCBV.add(lbBuscarPorTipo, 2, 1);
        gpCBV.add(tfBuscarPorTipo, 3, 1);
        gpCBV.add(btnBuscarVenta, 4, 1);
        
        Pane tvPane = new Pane();
        
        List<String> lstWhere = new ArrayList<>();
        lstWhere.add("codigo_prod is not null");

        Label lbProducto = new Label("Tabla Producto Seleccionados: ");
        Label lbVenta = new Label("Tabla Ventas Seleccionadas: ");
        TableView tvProductosSelecc = new TableView();
        tvProductosSelecc.setPrefHeight(350);
        tvProductosSelecc.setPrefWidth(550);
        tvProductosSelecc.setContextMenu(cmTabVentas);
        //id_detalle_venta clave_prod cantidad codigo_nota_venta 
        //codigo_prod precio_menudeo precio_mayoreo descrprod 
        
        TableColumn<detalle_venta, Integer> idDetalleVentaColumna = new TableColumn<>("Id Detalle Venta");
        idDetalleVentaColumna.setMinWidth(120);
        idDetalleVentaColumna.setCellValueFactory(new PropertyValueFactory<>("id_detalle_venta"));
        
        TableColumn<detalle_venta, Integer> codigoProductColumna = new TableColumn<>("Codigo Producto");
        codigoProductColumna.setMinWidth(80);
        codigoProductColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));
        
        TableColumn<detalle_venta, String> descrProductColumna = new TableColumn<>("Descripción Producto");
        descrProductColumna.setMinWidth(220);
        descrProductColumna.setCellValueFactory(new PropertyValueFactory<>("descrprod"));
        
        TableColumn<detalle_venta, Integer> cantidadProductColumna = new TableColumn<>("Cantidad");
        cantidadProductColumna.setMinWidth(80);
        cantidadProductColumna.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        
        TableColumn<detalle_venta, Integer> codigoNotaVentaColumna = new TableColumn<>("Codigo Nota Venta");
        codigoNotaVentaColumna.setMinWidth(120);
        codigoNotaVentaColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_nota_venta"));

        TableColumn<detalle_venta, Integer> precioVentaColumna = new TableColumn<>("Precio Venta");
        precioVentaColumna.setMinWidth(120);
        precioVentaColumna.setCellValueFactory(new PropertyValueFactory<>("precio_venta"));
        
        TableColumn<detalle_venta, Integer> subtotalColumna = new TableColumn<>("Sub-total");
        subtotalColumna.setMinWidth(120);
        subtotalColumna.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
        
        tvProductosSelecc.getColumns().addAll(codigoProductColumna, descrProductColumna, cantidadProductColumna, precioVentaColumna, subtotalColumna);
        tvProductosSelecc.setItems(detventa);
        
        miEliminarDetVenta.setOnAction((event) -> {
            detalle_venta dtvtaTemp = (detalle_venta) tvProductosSelecc.getSelectionModel().getSelectedItem();
            lstWhere.clear();
            lstWhere.add("codigo_prod = "+ dtvtaTemp.getCodigo_prod());
            inventario iv =  invent.consultaInventario(lstWhere).get(0);
            int nvaCantidad = iv.getExistencia()+dtvtaTemp.getCantidad();
            invent.modificarExistenciaProducto(dtvtaTemp.getCodigo_prod(), nvaCantidad);
            detventaDAO.borrarDetVenta(dtvtaTemp.getId_detalle_venta());
            detventa.clear();
            lstWhereConcepto.clear();
            lstWhereConcepto.add("codigo_nota_venta = "+dtvtaTemp.getCodigo_nota_venta());
            detventa = FXCollections.observableArrayList(detventaDAO.consultaDetVenta(lstWhereConcepto));
            lbMontoTotal.setText("0.0");
            for (detalle_venta detv : detventa){
                lstWhere.clear();
                lstWhere.add("codigo_prod = "+detv.getCodigo_prod());
                inventario ivTemp = invent.consultaInventario(lstWhere).get(0);
                detv.setExistencia(ivTemp.getExistencia());
                detv.setSubTotal(detv.getCantidad()* detv.getPrecio_venta());
                float MontoTotal = Float.parseFloat(lbMontoTotal.getText());
                MontoTotal = MontoTotal + detv.getSubTotal();
                lbMontoTotal.setText(Float.toString(MontoTotal));               
            }
            tvProductosSelecc.setItems(detventa);
            
        });

       
        tvTablaVentas.setOnMouseClicked((event) -> {
            
            VENTA vtaTemp = (VENTA) tvTablaVentas.getSelectionModel().getSelectedItem();
            lstWhereConcepto.clear();
            lstWhereConcepto.add("codigo_cliente = "+vtaTemp.getCodigo_cliente());
            CLIENTE cliIden = cliDAO.consultarClientes(lstWhereConcepto).get(0);
            tfCodigoCliente.setText(String.valueOf(cliIden.getCodigo_cliente()));
            tfNombre.setText(cliIden.getNombre());
            tfRazonSocial.setText(cliIden.getRazon_social());
            tfDomicilioFiscal.setText(cliIden.getDomicilio_fiscal());
            tfEmail.setText(cliIden.getEmail());
            tfTelefono.setText(cliIden.getTelefono());
            tfRFC.setText(cliIden.getRfc());
            cbTipoVenta.setValue(vtaTemp.getTipo_venta());
            dpFecha.setValue(LocalDate.parse(vtaTemp.getFecha()));
            tfCodigoFactura.setText(vtaTemp.getCodigo_factura());
            lstWhereConcepto.clear();
            lstWhereConcepto.add("id_nota_rem = "+vtaTemp.getId_nota_rem());
            notas_remision notasRemTemp = notasDAO.consultarNotasRem(lstWhereConcepto).get(0);
            tfFolio.setText(String.valueOf(notasRemTemp.getFolio()));
            if(!detventa.isEmpty()){
               detventa.clear();
            }
            lstWhereConcepto.clear();
            lstWhereConcepto.add("codigo_nota_venta = "+vtaTemp.getCodigo_nota_venta());
            detventa = FXCollections.observableArrayList(detventaDAO.consultaDetVenta(lstWhereConcepto));
            lbMontoTotal.setText("0.0");
            for (detalle_venta detv : detventa){
                lstWhere.clear();
                lstWhere.add("codigo_prod = "+detv.getCodigo_prod());
                inventario ivTemp = invent.consultaInventario(lstWhere).get(0);
                detv.setExistencia(ivTemp.getExistencia());
                detv.setSubTotal(detv.getCantidad()* detv.getPrecio_venta());
                float MontoTotal = Float.parseFloat(lbMontoTotal.getText());
                MontoTotal = MontoTotal + detv.getSubTotal();
                lbMontoTotal.setText(Float.toString(MontoTotal));               
            }
            tvProductosSelecc.setItems(detventa);
        });        
        
        GridPane gpClienteSeleccionado = new GridPane();
        gpClienteSeleccionado.setPadding(new Insets(5, 5, 5, 5));
        gpClienteSeleccionado.setVgap(10);
        gpClienteSeleccionado.setHgap(10);
        
        gpClienteSeleccionado.add(lbCodigo_cliente, 0, 0);
        gpClienteSeleccionado.add(tfCodigoCliente, 1, 0);
        gpClienteSeleccionado.add(lbNombre, 2, 0);
        gpClienteSeleccionado.add(tfNombre, 3, 0);
        
        gpClienteSeleccionado.add(lbRazon_social, 0, 1);
        gpClienteSeleccionado.add(tfRazonSocial, 1, 1);
        gpClienteSeleccionado.add(lbDomicilio_fiscal, 2, 1);
        gpClienteSeleccionado.add(tfDomicilioFiscal, 3, 1);
        
        gpClienteSeleccionado.add(lbTelefono, 0, 2);
        gpClienteSeleccionado.add(tfTelefono, 1, 2);
        gpClienteSeleccionado.add(lbRFC, 2, 2);
        gpClienteSeleccionado.add(tfRFC, 3, 2);
        
        gpClienteSeleccionado.add(lbEmail, 0, 3);
        gpClienteSeleccionado.add(tfEmail, 1, 3);
        
        VBox vbHead = new VBox();

        HBox hbTipoSeleccion = new HBox();
        hbTipoSeleccion.getChildren().addAll(gpCBV);
        hbTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        hbTipoSeleccion.setSpacing(5);
        
        HBox hbTotal = new HBox();
        hbTotal.setPrefWidth(400);
        hbTotal.setMaxWidth(500);
        hbTotal.setMinWidth(400);
        hbTotal.setSpacing(10);
        hbTotal.setAlignment(Pos.CENTER_RIGHT);
        hbTotal.getChildren().addAll(lbEtiquetaMonto, lbMontoTotal);  
        
        HBox hbCompSeleccion = new HBox();
        
        hbCompSeleccion.getChildren().addAll(hbTipoSeleccion, hbTotal);
        hbCompSeleccion.setPadding(new Insets(10, 10, 10, 10));
        hbCompSeleccion.setSpacing(5);

        Separator spSeleccionProductos = new Separator();
        vbHead.getChildren().addAll(hbCompSeleccion, spSeleccionProductos);
        
        VBox vbTabVenta = new VBox();
        vbTabVenta.setPadding(new Insets(5, 5, 5, 5));
        vbTabVenta.setSpacing(5);
        
        vbTabVenta.getChildren().addAll(lbVenta, tvTablaVentas, lbProducto, tvProductosSelecc);
        
        Separator spVenta = new Separator();
        Separator spProducto =  new Separator();
        
        GridPane gpBloqueVenta = new GridPane();
        gpBloqueVenta.setPadding(new Insets(5, 5, 5, 5));
        gpBloqueVenta.setVgap(10);
        gpBloqueVenta.setHgap(10);
        
        gpBloqueVenta.add(lbTipo_venta, 0, 0);
        gpBloqueVenta.add(cbTipoVenta , 1, 0);

        gpBloqueVenta.add(lbFecha , 2, 0);
        gpBloqueVenta.add(dpFecha , 3, 0);

        
        gpBloqueVenta.add(lbCodigoFactura , 0, 1);
        gpBloqueVenta.add(tfCodigoFactura , 1, 1);
               
        gpBloqueVenta.add(lbFolio , 2, 1);
        gpBloqueVenta.add(tfFolio, 3, 1);

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        
        Button btnEliminar = new Button("Eliminar Venta");
        btnEliminar.setOnAction((event) -> {
                   for (detalle_venta dtvTemp : detventa){
                        lstWhere.clear();
                        lstWhere.add("codigo_prod = "+ dtvTemp.getCodigo_prod());
                        inventario iv =  invent.consultaInventario(lstWhere).get(0);
                        int nvaCantidad = iv.getExistencia()+dtvTemp.getCantidad();
                        invent.modificarExistenciaProducto(dtvTemp.getCodigo_prod(), nvaCantidad);
                        detventaDAO.borrarDetVenta(dtvTemp.getId_detalle_venta());
                   }
                   VENTA vta = (VENTA) tvTablaVentas.getSelectionModel().getSelectedItem();
                   String strTipoVenta = cbTipoVenta.getSelectionModel().getSelectedItem().toString();
                           if (strTipoVenta.contains("EFECTIVO")==true){
                                   notasDAO.borrarNotasRem(vta.getId_nota_rem());
                                   ventDAO.borrarVenta(vta.getCodigo_nota_venta());
                                   Alert altMensaje = new Alert(Alert.AlertType.INFORMATION);
                                   altMensaje.setContentText("Venta en Efectivo Eliminada");
                                   altMensaje.setTitle("Informacion-Venta");
                                   altMensaje.show();
                            } // "Transferencia"
                           if (strTipoVenta.compareTo("VENTA A CREDITO")==0){
                                  lstWhere.clear();
                                  lstWhere.add("id_nota_rem = "+vta.getId_nota_rem());
                                  notas_remision ntRemTEmp =notasDAO.consultarNotasRem(lstWhere).get(0);
                                  lstWhere.clear();
                                  lstWhere.add("codigo_nota_venta = '"+vta.getCodigo_nota_venta()+"'");
                                  Credito creTemp = creDao.consultaCredito(lstWhere).get(0);
                                  lstWhere.clear();
                                  lstWhere.add("id_credito = "+creTemp.getId_credito());
                                  List<pagos_credito> lstPagosCredTemp =pagcreDAO.consultaPagoCred(lstWhere);
                                  for (pagos_credito PagosCredTemp : lstPagosCredTemp){
                                        pagcreDAO.borrarPagoCred(PagosCredTemp.getId_credito());
                                   }
                                   creDao.borrarCredito(creTemp.getId_credito());
                                   notasDAO.borrarNotasRem(vta.getId_nota_rem());
                                   ventDAO.borrarVenta(vta.getCodigo_nota_venta());                              
                                   Alert altMensaje = new Alert(Alert.AlertType.INFORMATION);
                                   altMensaje.setContentText("Venta a Credito Eliminada");
                                   altMensaje.setTitle("Informacion-Venta");
                                   altMensaje.show();
                            }            
                            if (strTipoVenta.compareTo("APARTADO")==0){
                                  lstWhere.clear();
                                  lstWhere.add("id_nota_rem = "+vta.getId_nota_rem());
                                  notas_remision ntRemTEmp =notasDAO.consultarNotasRem(lstWhere).get(0);
                                  lstWhere.clear();
                                  lstWhere.add("codigo_nota_venta = "+vta.getCodigo_nota_venta());
                                  apartado aptTemp = apaDAO.consultaApartado(lstWhere).get(0);
                                  lstWhere.clear();
                                  lstWhere.add("id_apartado = "+aptTemp.getId_apartado());
                                  List<pagos_apartado> lstPagosApartadosTemp =pagapaDAO.consultaPagosAP(lstWhere);
                                  for (pagos_apartado PagosApartadosTemp : lstPagosApartadosTemp){
                                        pagapaDAO.borrarPagosAP(PagosApartadosTemp.getId_apartado());
                                   }
                                  apaDAO.borrarApartado(aptTemp.getId_apartado());
                                  notasDAO.borrarNotasRem(vta.getId_nota_rem());
                                  ventDAO.borrarVenta(vta.getCodigo_nota_venta());
                                  Alert altMensaje =new Alert(Alert.AlertType.INFORMATION);
                                  altMensaje.setContentText("Venta en Apartado Eliminada");
                                  altMensaje.setTitle("Informacion-Venta");
                                  altMensaje.show();
                            } 
                            if (strTipoVenta.compareTo("TARJETA")==0){
                                   notasDAO.borrarNotasRem(vta.getId_nota_rem());
                                   ventDAO.borrarVenta(vta.getCodigo_nota_venta());
                                  Alert altMensaje =new Alert(Alert.AlertType.INFORMATION);
                                  altMensaje.setContentText("Venta Con Tarjeta de credito Registrada");
                                  altMensaje.setTitle("Informacion-Venta");
                                  altMensaje.show();
                            } 
                            if (strTipoVenta.compareTo("TRANSFERENCIA")==0){
                                   notasDAO.borrarNotasRem(vta.getId_nota_rem());
                                   ventDAO.borrarVenta(vta.getCodigo_nota_venta());
                                   Alert altMensaje =new Alert(Alert.AlertType.INFORMATION);
                                   altMensaje.setContentText("Venta con Transferencia Registrada");
                                   altMensaje.setTitle("Informacion-Venta");
                                   altMensaje.show();
                            }
                             tfCodigoCliente.setText("");
                             tfNombre.setText("");
                             tfRazonSocial.setText("");
                             tfDomicilioFiscal.setText("");
                             tfTelefono.setText("");
                             tfRFC.setText("");
                             tfEmail.setText(""); 
                             tfCodigoCliente.setEditable(true);
                             tfNombre.setEditable(true);
                             tfRazonSocial.setEditable(true);
                             tfDomicilioFiscal.setEditable(true);
                             tfTelefono.setEditable(true);
                             tfRFC.setEditable(true);
                             tfEmail.setEditable(true);
                            removerVistas();
        });
        
        HBox hbBotonesInferiores = new HBox();
        hbBotonesInferiores.setAlignment(Pos.CENTER_RIGHT);
        hbBotonesInferiores.getChildren().addAll(btnCancelar, btnEliminar);
        
        VBox vbTabProducto = new VBox();
        vbTabProducto.setSpacing(5);
        vbTabProducto.getChildren().addAll(gpClienteSeleccionado, spVenta, gpBloqueVenta, hbBotonesInferiores);
        
        
        HBox hbBody = new HBox();
        hbBody.getChildren().addAll(vbTabVenta, vbTabProducto);
        
        vbVistaPpal.getChildren().addAll(lbTituloVista, vbHead, hbBody);
        
        return vbVistaPpal; 
    }    
    private VBox vistaCrearCotizacion(){
        
         if (detventa.size()>0){
            detventa = FXCollections.observableArrayList();
        }
        
        VBox vbVistaPpal = new VBox();
        
        //Etiquetas/Datos de la Venta
        Label lbEtiquetaMonto = new Label("TOTAL: $ ");
        Label lbMontoTotal = new Label("0.0");
        Font fuente = new Font("Arial Bold", 36);
        lbEtiquetaMonto.setFont(fuente);
        lbMontoTotal.setFont(fuente);
        
	Label lbCantidad  = new Label("Cantidad");
	Label lbCodigoProd  = new Label("Codigo Producto: ");
        Label lbDescProducto = new Label ("Descripción Producto: ");
	Label lbPrecioMenudeo  = new Label("Precio Menudeo: ");
	Label lbPrecioMayoreo  = new Label("Precio Mayoreo: ");
        Label lbPrecioVenta = new Label("Precio Venta");
        
        ObservableList<String> lstOpcionesTipoVenta = FXCollections.observableArrayList("Efectivo","Credito", "Apartado", "Tarjeta Credito", "Transferencia");
        ComboBox cbTipoVenta = new ComboBox(lstOpcionesTipoVenta);
        cbTipoVenta.setPrefWidth(180);
        
        TextField tfCantidad = new TextField();
        tfCantidad.setMaxWidth(80);
        TextField tfCodigoFactura = new TextField();
        
        TextField tfCodigoProducto = new TextField();
        TextField tfDescrProd = new TextField();
        tfDescrProd.setMaxWidth(300);
        tfDescrProd.setPrefWidth(300);
        TextField tfPrecioMayor = new TextField();
        tfPrecioMayor.setMaxWidth(120);
        tfPrecioMayor.setPrefWidth(120);
        TextField tfPrecioMenudeo = new TextField();
        TextField tfPrecioVenta = new TextField();
        
        //Componentes de Interfaz
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgBusquedas = new ToggleGroup();
        
        RadioButton rbTodos = new RadioButton("Todos");
        RadioButton rbCodigo = new RadioButton("Codigo");
        RadioButton rbDescripcion = new RadioButton("Descripción");
        RadioButton rbCategoria = new RadioButton("Categoria");
        rbTodos.setSelected(true);
        
        rbTodos.setToggleGroup(tgBusquedas);
        rbCodigo.setToggleGroup(tgBusquedas);
        rbDescripcion.setToggleGroup(tgBusquedas);
        rbCategoria.setToggleGroup(tgBusquedas);
        
        ToggleGroup tgPrecio = new ToggleGroup();
        RadioButton rbPrecioMenudeo =  new RadioButton("Precio Menudeo");
        rbPrecioMenudeo.setOnAction((ActionEvent e)->{
            tfPrecioVenta.setText(tfPrecioMenudeo.getText());
        });
        
        RadioButton rbPrecioMayoreo = new RadioButton("Precio Mayoreo");
        rbPrecioMayoreo.setOnAction((ActionEvent e)->{
            tfPrecioVenta.setText(tfPrecioMayor.getText());
        });
                
        rbPrecioMenudeo.setToggleGroup(tgPrecio);
        rbPrecioMayoreo.setToggleGroup(tgPrecio);
        
       List<String> lstCategorias = new ArrayList<>();
       List<String> lstWherecat = new ArrayList<>();
       lstWherecat.add("id_categoria is not null");
       for (categoria i : categDAO.consultarCategoria(lstWherecat)){
            lstCategorias.add(i.getCategoria());
       }
        
        Label lbCodigo = new Label("Codigo: ");
        TextField tfCodigo = new TextField();
        Label lbDescripcion = new Label("Descripción: ");
        TextField tfDescripcion = new TextField();
        Label lbCategoria = new Label("Categoria: ");
        ComboBox cbCategoria = new ComboBox(FXCollections.observableArrayList(lstCategorias));
        cbCategoria.setPrefWidth(140);
        
        Label lbInventario = new Label("Tabla Inventario: ");
        TableView tvInventario = new TableView();
        tvInventario.setPrefHeight(350);
        tvInventario.setPrefWidth(550);
        
        TableColumn<inventario, Integer> claveProdColumna = new TableColumn<>("Codigo Producto");
        claveProdColumna.setMinWidth(120);
        claveProdColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));

        TableColumn<inventario, Integer> existenciaColumna = new TableColumn<>("Existencia");
        existenciaColumna.setMinWidth(120);
        existenciaColumna.setCellValueFactory(new PropertyValueFactory<>("existencia"));
        
        TableColumn<inventario, Integer> idUbicacionColumna = new TableColumn<>("Id Ubicación");
        idUbicacionColumna.setMinWidth(120);
        idUbicacionColumna.setCellValueFactory(new PropertyValueFactory<>("id_ubicacion"));
        
        TableColumn<inventario, Float> pMenudeoColumna = new TableColumn<>("Precio Menudeo");
        pMenudeoColumna.setMinWidth(120);
        pMenudeoColumna.setCellValueFactory(new PropertyValueFactory<>("precio_menudeo"));        

        TableColumn<inventario, Float> pMayoreoColumna = new TableColumn<>("Precio Mayoreo");
        pMayoreoColumna.setMinWidth(120);
        pMayoreoColumna.setCellValueFactory(new PropertyValueFactory<>("precio_mayoreo"));
        
        TableColumn<inventario, String> descripcionColumna = new TableColumn<>("Descripción");
        descripcionColumna.setMinWidth(120);
        descripcionColumna.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        
        TableColumn<inventario, String> uMedidaColumna = new TableColumn<>("Unidad Medidad");
        uMedidaColumna.setMinWidth(120);
        uMedidaColumna.setCellValueFactory(new PropertyValueFactory<>("unidad_medida"));

        TableColumn<inventario, Float> cCompraColumna = new TableColumn<>("Costo Compra");
        cCompraColumna.setMinWidth(120);
        cCompraColumna.setCellValueFactory(new PropertyValueFactory<>("costo_compra"));
        
        TableColumn<inventario, Integer> codProvColumna = new TableColumn<>("Codigo Proveedor");
        codProvColumna.setMinWidth(120);
        codProvColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prov"));
        
        tvInventario.getColumns().addAll(claveProdColumna, descripcionColumna, existenciaColumna,
                pMenudeoColumna, pMayoreoColumna, uMedidaColumna, idUbicacionColumna, 
                codProvColumna, cCompraColumna);
        List<String> lstWhere = new ArrayList<>();
        lstWhere.add("codigo_prod is not null");
        tvInventario.setItems(invent.consultarInventario(lstWhere));
        
        tvInventario.setOnMouseClicked((event) -> {
            inventario inv = new inventario();
            inv = (inventario) tvInventario.getSelectionModel().getSelectedItem();
            tfCodigoProducto.setText(String.valueOf(inv.getCodigo_prod()));
            tfDescrProd.setText(inv.getDescripcion());
            tfPrecioMenudeo.setText(String.valueOf(inv.getPrecio_menudeo()));
            tfPrecioMayor.setText(String.valueOf(inv.getPrecio_mayoreo()));
        });
        Label lbProducto = new Label("Tabla Producto Seleccionados: ");
        TableView tvProductosSelecc = new TableView();
        tvProductosSelecc.setPrefHeight(350);
        tvProductosSelecc.setPrefWidth(550);
        
        TableColumn<detalle_venta, Integer> idDetalleVentaColumna = new TableColumn<>("Id Detalle Venta");
        idDetalleVentaColumna.setMinWidth(120);
        idDetalleVentaColumna.setCellValueFactory(new PropertyValueFactory<>("id_detalle_venta"));
        
        TableColumn<detalle_venta, Integer> codigoProductColumna = new TableColumn<>("Codigo Producto");
        codigoProductColumna.setMinWidth(80);
        codigoProductColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));
        
        TableColumn<detalle_venta, String> descrProductColumna = new TableColumn<>("Descripción Producto");
        descrProductColumna.setMinWidth(220);
        descrProductColumna.setCellValueFactory(new PropertyValueFactory<>("descrprod"));
        
        TableColumn<detalle_venta, Integer> cantidadProductColumna = new TableColumn<>("Cantidad");
        cantidadProductColumna.setMinWidth(80);
        cantidadProductColumna.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        
        TableColumn<detalle_venta, Integer> codigoNotaVentaColumna = new TableColumn<>("Codigo Nota Venta");
        codigoNotaVentaColumna.setMinWidth(120);
        codigoNotaVentaColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_nota_venta"));

        TableColumn<detalle_venta, Integer> precioVentaColumna = new TableColumn<>("Precio Venta");
        precioVentaColumna.setMinWidth(120);
        precioVentaColumna.setCellValueFactory(new PropertyValueFactory<>("precio_venta"));
        
        TableColumn<detalle_venta, Integer> subtotalColumna = new TableColumn<>("Sub-total");
        subtotalColumna.setMinWidth(120);
        subtotalColumna.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
        
        tvProductosSelecc.getColumns().addAll(codigoProductColumna, descrProductColumna, cantidadProductColumna, precioVentaColumna, subtotalColumna);
        tvProductosSelecc.setItems(detventa);

        Button btnAgregarProducto = new Button("Agregar Producto");
        btnAgregarProducto.setOnAction((ActionEvent e)->{
            detalle_venta detvta = new detalle_venta();
            detvta.setCodigo_prod(Integer.parseInt(tfCodigoProducto.getText()));
            detvta.setDescrprod(tfDescrProd.getText());
            detvta.setCantidad(Integer.parseInt(tfCantidad.getText()));
            detvta.setPrecio_venta(Float.parseFloat(tfPrecioVenta.getText()));
            detvta.setSubTotal(Integer.parseInt(tfCantidad.getText())* Float.parseFloat(tfPrecioVenta.getText()));
            float MontoTotal = Float.parseFloat(lbMontoTotal.getText());
            MontoTotal = MontoTotal + Integer.parseInt(tfCantidad.getText())* Float.parseFloat(tfPrecioVenta.getText());
            lbMontoTotal.setText(Float.toString(MontoTotal));
            detventa.add(detvta);
        });
        
        GridPane gpClienteSeleccionado = new GridPane();
        gpClienteSeleccionado.setPadding(new Insets(5, 5, 5, 5));
        gpClienteSeleccionado.setVgap(10);
        gpClienteSeleccionado.setHgap(10);
        
        VBox vbHead = new VBox();

        HBox hbTipoSeleccion = new HBox();
        hbTipoSeleccion.getChildren().addAll(rbTodos, rbCodigo, rbDescripcion, rbCategoria);
        hbTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        hbTipoSeleccion.setSpacing(5);
        
        VBox vbCodigo = new VBox();
        vbCodigo.getChildren().addAll(lbCodigo, tfCodigo);
        vbCodigo.setSpacing(5);
        
        VBox vbDesc = new VBox();
        vbDesc.getChildren().addAll(lbDescripcion, tfDescripcion);
        vbDesc.setSpacing(5);
        
        VBox vbCat = new VBox();
        vbCat.getChildren().addAll(lbCategoria, cbCategoria);
        vbCat.setSpacing(5);
        
        
        HBox hbCompSeleccion = new HBox();
        Button btnBuscarProductos = new Button("Seleccionar");
        btnBuscarProductos.setMaxHeight(50);
        btnBuscarProductos.setOnAction((ActionEvent e)->{
         List<inventario> lstInv = new ArrayList<>();
         List<String> lstWherelc = new ArrayList<>();
         
          if (rbTodos.isSelected()){
           lstWherelc.add("codigo_prod is not null");
           tvInventario.setItems(invent.consultarInventario(lstWherelc));
         } 
         
         if (rbCodigo.isSelected()){
           lstWhere.add("codigo_prod = "+tfCodigo.getText());
           tvInventario.setItems(invent.consultarInventario(lstWhere));
         }
         if (rbDescripcion.isSelected()){
           lstWherelc.add("descripcion like '%"+tfDescripcion.getText()+"%' ");
           tvInventario.setItems(invent.consultarInventario(lstWherelc));
         }
         
         if (rbCategoria.isSelected()){
           lstWherecat.add("id_categoria is not null");  
           for (categoria i : categDAO.consultarCategoria(lstWherecat)){
             String strCategoria =  i.getCategoria();
             if (strCategoria.compareTo(cbCategoria.getSelectionModel().getSelectedItem().toString())==0){
                  lstWhere.add("id_categoria = "+i.getId_categoria());
                  tvInventario.setItems(invent.consultarInventario(lstWhere));
             }
           }
         }
        });
        
        HBox hbTotal = new HBox();
        hbTotal.setPrefWidth(400);
        hbTotal.setMaxWidth(500);
        hbTotal.setMinWidth(400);
        hbTotal.setSpacing(10);
        hbTotal.setAlignment(Pos.CENTER_RIGHT);
        hbTotal.getChildren().addAll(lbEtiquetaMonto, lbMontoTotal);
        
        hbCompSeleccion.getChildren().addAll(vbCodigo, vbDesc, vbCat, btnBuscarProductos, hbTotal);
        hbCompSeleccion.setPadding(new Insets(10, 10, 10, 10));
        hbCompSeleccion.setSpacing(5);

        Separator spSeleccionProductos = new Separator();
        vbHead.getChildren().addAll(lbTipoBusqueda, hbTipoSeleccion, hbCompSeleccion, spSeleccionProductos);
        
        VBox vbTabInventario = new VBox();
        vbTabInventario.setPadding(new Insets(5, 5, 5, 5));
        
        
        vbTabInventario.getChildren().addAll(lbInventario, tvInventario, lbProducto, tvProductosSelecc);
        
        GridPane gpBloqueProducto = new GridPane();
        gpBloqueProducto.setPadding(new Insets(5, 5, 5, 5));
        gpBloqueProducto.setVgap(10);
        gpBloqueProducto.setHgap(10);
        
        gpBloqueProducto.add(lbCodigoProd , 0, 0);
        gpBloqueProducto.add(tfCodigoProducto , 1, 0);
        
        gpBloqueProducto.add(lbCantidad , 2, 0);
        gpBloqueProducto.add(tfCantidad , 3, 0);
        
        gpBloqueProducto.add(lbDescProducto , 2, 1);
        gpBloqueProducto.add(tfDescrProd , 3, 1);
        
        gpBloqueProducto.add(rbPrecioMenudeo, 0, 2);
        gpBloqueProducto.add(rbPrecioMayoreo, 2, 2);

        gpBloqueProducto.add(lbPrecioMenudeo , 0, 3);
        gpBloqueProducto.add(tfPrecioMenudeo , 1, 3);

        gpBloqueProducto.add(lbPrecioMayoreo , 2, 3);
        gpBloqueProducto.add(tfPrecioMayor , 3, 3);
        
        gpBloqueProducto.add(lbPrecioVenta, 0, 4);
        gpBloqueProducto.add(tfPrecioVenta, 1, 4);
        
        gpBloqueProducto.add(btnAgregarProducto, 2, 4);
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        
        Button btnGuardar = new Button("Generar Cotizacion");
        btnGuardar.setOnAction((event) -> {
          try{
            
           /* User home directory location */
           // String userHomeDirectory = System.getProperty("user.home");
            /* Output file location */
            
            File file;
            JasperReport jasperReport;
            file = new File("Reportes/Formatos/cotizacion.jasper");
            jasperReport = (JasperReport) JRLoader.loadObject(file);
            LocalDateTime ld = LocalDateTime.now();
            String fechaFile = String.valueOf(ld.getDayOfMonth())+String.valueOf(ld.getMonth())+String.valueOf(ld.getYear())+String.valueOf(ld.getHour())+String.valueOf(ld.getMinute())+String.valueOf(ld.getSecond());
            //String outputFile = userHomeDirectory + File.separatorChar + "ReporteInventario"+fechaFile+".pdf";
            String outputFile = "Reportes/Cotizacion/" + File.separatorChar + "ReporteInventario"+fechaFile+".pdf";
           //JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tvProductos.getItems().subList(0, tvProductos.getItems().size()-1));
           JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tvProductosSelecc.getItems());
           System.out.println("Hay "+tvProductosSelecc.getItems().size());
           Map<String, Object> parameters = new HashMap<>();
           parameters.put("ItemsDataSource", itemsJRBean);
           float total = Float.valueOf(lbMontoTotal.getText());
           parameters.put("total", total);
           //parameters.put("Fecha", LocalDate.now().toString());
           /* Generando el PDF */
            //C:\Users\dopcan\Documents\NetBeansProjects\ClasesConsultorio\src\gestionconsultorio
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            JRViewer jrViewer;
            JPanel jpanel;
            SwingNode swingNode;
            jpanel = new JPanel();
            swingNode = new SwingNode();
            jrViewer = new JRViewer(jasperPrint);
            jrViewer.setBounds(0, 0, 1200, 800);
            jpanel.setLayout(null);
            jpanel.add(jrViewer);
            jpanel.setSize(1200, 800);
            Pane panePreview = new Pane(); 
            panePreview.setPrefSize(1200, 800);
            panePreview.getChildren().add(swingNode);
            swingNode.setContent(jpanel);

            StackPane rootSelectClientes = new StackPane();
            rootSelectClientes.getChildren().addAll(swingNode);
       
            Scene scene = new Scene(rootSelectClientes,1200,800);
            Stage stgPpal = new Stage();
            stgPpal.setScene(scene);
            stgPpal.initModality(Modality.WINDOW_MODAL);
            stgPpal.show();  
        } catch (JRException ex) {
            ex.printStackTrace();
        }             
        });
        
        HBox hbBotonesInferiores = new HBox();
        hbBotonesInferiores.setAlignment(Pos.CENTER_RIGHT);
        hbBotonesInferiores.getChildren().addAll(btnCancelar, btnGuardar);
        
        VBox vbTabProducto = new VBox();
        vbTabProducto.setSpacing(5);
        vbTabProducto.getChildren().addAll(gpBloqueProducto, hbBotonesInferiores);
        
        
        HBox hbBody = new HBox();
        hbBody.getChildren().addAll(vbTabInventario, vbTabProducto);
        
        vbVistaPpal.getChildren().addAll(vbHead,hbBody);
        
        return vbVistaPpal; 
    }
    private VBox vistaModificarNotas(){
        
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("CONSULTAR/MODIFICAR NOTAS REMISION");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        //Componentes de seleccion
        
        Label lbBuscarPor = new Label("Buscar por:");
        Label lbBuscarPorFecha = new Label("fecha:");  
        Label lbBuscarPorTipo = new Label("Tipo venta:");  
        
        RadioButton rbPorFecha = new RadioButton("Buscar por fechas");
        RadioButton rbPorTipoRemision = new RadioButton("Buscar por Tipo Remision");
        ToggleGroup tgBuscarPor = new ToggleGroup();
        rbPorFecha.setToggleGroup(tgBuscarPor);
        rbPorTipoRemision.setToggleGroup(tgBuscarPor);
        rbPorFecha.setSelected(true);
        
        DatePicker dpFechas = new DatePicker(LocalDate.now());
        TextField tfTipoVenta = new TextField();
        
        Button btnBuscar = new Button("Seleccionar");
        
        GridPane gpCompSeleccion = new GridPane();
        gpCompSeleccion.setVgap(10);
        gpCompSeleccion.setHgap(10);
        gpCompSeleccion.add(lbBuscarPor, 0, 0);
        gpCompSeleccion.add(rbPorFecha, 1, 0);
        gpCompSeleccion.add(rbPorTipoRemision, 2, 0);
        gpCompSeleccion.add(lbBuscarPorFecha, 0, 1);
        gpCompSeleccion.add(dpFechas, 1, 1);
        gpCompSeleccion.add(lbBuscarPorTipo, 0, 2);
        gpCompSeleccion.add(tfTipoVenta, 1, 2);
        gpCompSeleccion.add(btnBuscar, 2, 2);
        
        List<notas_remision> lstNotasRemision = new ArrayList();
        List<String> lstWhere = new ArrayList();
        lstWhere.add("fecha = '"+dpFechas.getValue().toString()+"' ");
        obList.setAll(notasDAO.consultarNotasRem(lstWhere));
        TableView tvNotasRemision = new TableView();
        //id_nota_rem, folio,fecha, tipo_operacion ,monto,flag
        TableColumn idNotaREmColumna = new TableColumn("Id Nota Remision");
        idNotaREmColumna.setMinWidth(120);
        idNotaREmColumna.setCellValueFactory(new PropertyValueFactory<>("id_nota_rem"));
        
        TableColumn folioColumna = new TableColumn("Folio");
        folioColumna.setMinWidth(180);
        folioColumna.setCellValueFactory(new PropertyValueFactory<>("folio"));

        TableColumn FechaColumna = new TableColumn("Fecha");
        FechaColumna.setMinWidth(120);
        FechaColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        
        TableColumn tipOperColumna = new TableColumn("Tipo Operacion");
        tipOperColumna.setMinWidth(180);
        tipOperColumna.setCellValueFactory(new PropertyValueFactory<>("tipo_operacion"));

        TableColumn montoColumna = new TableColumn("Monto");
        montoColumna.setMinWidth(180);
        montoColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));
       
        tvNotasRemision.getColumns().addAll(idNotaREmColumna, folioColumna, FechaColumna, tipOperColumna, montoColumna);
        tvNotasRemision.setItems(obList);
        
        btnBuscar.setOnAction((event) -> {
            if (rbPorFecha.isSelected()){
                lstWhere.clear();
                lstWhere.add("fecha = '"+dpFechas.getValue().toString()+"' ");
                obList.setAll(notasDAO.consultarNotasRem(lstWhere));
                tvNotasRemision.setItems(obList);
            }
            if (rbPorTipoRemision.isSelected()){
                lstWhere.clear();
                lstWhere.add("tipo_operacion LIKE '%"+tfTipoVenta.getText()+"%' ");
                obList.setAll(notasDAO.consultarNotasRem(lstWhere));
                tvNotasRemision.setItems(obList);
            }
        });
        
         Label lbIdNotaRem = new Label("Id Nota Remision: ");
         Label lbFolio = new Label("Folio : ");
         Label lbFecha = new Label("Fecha: ");
         Label lbTiOper = new Label("Tipo Operacion: ");
         Label lbMonto = new Label("Monto: ");
         
         TextField tfIdNotaRem = new TextField();
           tfIdNotaRem.setEditable(false);
           tfIdNotaRem.setPrefWidth(80);
           tfIdNotaRem.setMaxWidth(80);
           tfIdNotaRem.setEditable(false);
         TextField tfFolio = new TextField();
           tfFolio.setPrefWidth(80);
           tfFolio.setMaxWidth(80);
         DatePicker dpFecha = new DatePicker();
         dpFecha.setMaxWidth(120);
         dpFecha.setDisable(true);
         ObservableList<String> lstTiOper = FXCollections.observableArrayList("Efectivo","Credito","Apartado","Tarjeta Credito", "Transferencia");
         ComboBox cbTiOper = new ComboBox(lstTiOper);
         cbTiOper.setDisable(true);
         TextField tfMonto = new TextField();
         tfMonto.setPrefWidth(80);
         tfMonto.setMaxWidth(80);
         
         Button btnCancelar = new Button("Salir");
         btnCancelar.setOnAction((ActionEvent e)->{
             if(vbAreaTrabajo.getChildren().size()>=0){
                 vbAreaTrabajo.getChildren().remove(0);
             }
         });
         
         Button btnModificar = new Button("Modificar");
         btnModificar.setOnAction((ActionEvent e)->{
           notRem.setId_nota_rem(Integer.parseInt(tfIdNotaRem.getText()));
           notRem.setFolio(tfFolio.getText());
           notRem.setFecha(dpFecha.getValue().toString());
           notRem.setMonto(Float.parseFloat(tfMonto.getText()));
           notRem.setTipo_operacion(cbTiOper.getValue().toString());
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setHeaderText(null);
           alert.setTitle("Confirmación");
           alert.setContentText("¿Estas seguro de confirmar la acción?");
           Optional<ButtonType> action = alert.showAndWait(); 
           if (action.get() == ButtonType.OK) {
              notasDAO.modificarNotasRem(notRem);
              removerVistas();
           } else {
              removerVistas();
           }            
            
         });
         
        tvNotasRemision.setOnMouseClicked((event) -> {
          //tfNombreProveedor.setText("Seleccionaste: "+ tvProveedores.getSelectionModel().getSelectedIndex());
          notas_remision notRemIde = (notas_remision)tvNotasRemision.getSelectionModel().getSelectedItem();
          tfIdNotaRem.setText(String.valueOf(notRemIde.getId_nota_rem()));
          tfFolio.setText(notRemIde.getFolio());
          dpFecha.setValue(LocalDate.parse(notRemIde.getFecha()));
          tfMonto.setText(String.valueOf(notRemIde.getMonto()));
          cbTiOper.setValue(notRemIde.getTipo_operacion());
         
        });
         
         HBox hbBotones = new HBox(btnCancelar, btnModificar);
         hbBotones.setSpacing(10);
        
         GridPane gpPrincipal = new GridPane();
         gpPrincipal.setPadding(new Insets(5, 5, 5, 5));
         gpPrincipal.setHgap(10);
         gpPrincipal.setVgap(10);
         
         gpPrincipal.add(lbIdNotaRem, 0, 0);
         gpPrincipal.add(tfIdNotaRem, 1, 0);
         gpPrincipal.add(lbFolio, 0, 1);
         gpPrincipal.add(tfFolio, 1, 1);
         gpPrincipal.add(lbFecha, 0, 2);
         gpPrincipal.add(dpFecha, 1, 2);
         gpPrincipal.add(lbTiOper, 0, 3);
         gpPrincipal.add(cbTiOper, 1, 3);
         gpPrincipal.add(lbMonto, 0, 4);
         gpPrincipal.add(tfMonto, 1, 4);
         gpPrincipal.add(hbBotones, 1, 5);
        
         vbPpal.getChildren().addAll(lbTituloVista, gpCompSeleccion, tvNotasRemision, gpPrincipal);
        return vbPpal;
    }
    private VBox vistaConsultarNotas(){
        
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("ELIMINAR NOTAS REMISION");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        List<notas_remision> lstNotasRemision = new ArrayList();
        List<String> lstWhere = new ArrayList();
        lstWhere.add("id_nota_rem is not null");
        obList.setAll(notasDAO.consultarNotasRem(lstWhere));
        TableView tvNotasRemision = new TableView();
        //id_nota_rem, folio,fecha, tipo_operacion ,monto,flag
        TableColumn idNotaREmColumna = new TableColumn("Id Nota Remision");
        idNotaREmColumna.setMinWidth(120);
        idNotaREmColumna.setCellValueFactory(new PropertyValueFactory<>("id_nota_rem"));
        
        TableColumn folioColumna = new TableColumn("Folio");
        folioColumna.setMinWidth(180);
        folioColumna.setCellValueFactory(new PropertyValueFactory<>("folio"));

        TableColumn FechaColumna = new TableColumn("Fecha");
        FechaColumna.setMinWidth(120);
        FechaColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        
        TableColumn tipOperColumna = new TableColumn("Tipo Operacion");
        tipOperColumna.setMinWidth(180);
        tipOperColumna.setCellValueFactory(new PropertyValueFactory<>("tipo_operacion"));

        TableColumn montoColumna = new TableColumn("Monto");
        montoColumna.setMinWidth(180);
        montoColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));
       
        tvNotasRemision.getColumns().addAll(idNotaREmColumna, folioColumna, FechaColumna, tipOperColumna, montoColumna);
        tvNotasRemision.setItems(obList);
        
         Label lbIdNotaRem = new Label("Id Nota Remision: ");
         Label lbFolio = new Label("Folio : ");
         Label lbFecha = new Label("Fecha: ");
         Label lbTiOper = new Label("Tipo Operacion: ");
         Label lbMonto = new Label("Monto: ");
         
         TextField tfIdNotaRem = new TextField();
           tfIdNotaRem.setPrefWidth(80);
           tfIdNotaRem.setMaxWidth(80);
           tfIdNotaRem.setEditable(false);
         TextField tfFolio = new TextField();
           tfFolio.setPrefWidth(80);
           tfFolio.setMaxWidth(80);
           tfFolio.setEditable(false);
         DatePicker dpFecha = new DatePicker();
         dpFecha.setMaxWidth(120);
         dpFecha.setEditable(false);
         ObservableList<String> lstTiOper = FXCollections.observableArrayList("Efectivo","Credito","Apartado","Tarjeta Credito", "Transferencia");
         ComboBox cbTiOper = new ComboBox(lstTiOper);
         cbTiOper.setEditable(false);
         TextField tfMonto = new TextField();
         tfMonto.setPrefWidth(80);
         tfMonto.setMaxWidth(80);
         tfMonto.setEditable(false);
         
         Button btnCancelar = new Button("Cancelar");
         btnCancelar.setOnAction((ActionEvent e)->{
             if(vbAreaTrabajo.getChildren().size()>=0){
                 vbAreaTrabajo.getChildren().remove(0);
             }
         });
         
         Button btnModificar = new Button("Modificar");
         btnModificar.setOnAction((ActionEvent e)->{
           notRem.setId_nota_rem(Integer.parseInt(tfIdNotaRem.getText()));
           notRem.setFolio(tfFolio.getText());
           notRem.setFecha(dpFecha.getValue().toString());
           notRem.setMonto(Float.parseFloat(tfMonto.getText()));
           notRem.setTipo_operacion(cbTiOper.getValue().toString());
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setHeaderText(null);
           alert.setTitle("Confirmación");
           alert.setContentText("¿Estas seguro de confirmar la acción?");
           Optional<ButtonType> action = alert.showAndWait(); 
           if (action.get() == ButtonType.OK) {
              //notasDAO.borrarNotasRem(notRem);
              removerVistas();
           } else {
              removerVistas();
           }            
            
         });
         
        tvNotasRemision.setOnMouseClicked((event) -> {
          //tfNombreProveedor.setText("Seleccionaste: "+ tvProveedores.getSelectionModel().getSelectedIndex());
          notas_remision notRemIde = (notas_remision)tvNotasRemision.getSelectionModel().getSelectedItem();
          tfIdNotaRem.setText(String.valueOf(notRemIde.getId_nota_rem()));
          tfFolio.setText(notRemIde.getFolio());
          dpFecha.setValue(LocalDate.parse(notRemIde.getFecha()));
          tfMonto.setText(String.valueOf(notRemIde.getMonto()));
          cbTiOper.setValue(notRemIde.getTipo_operacion());
         
        });
         
         HBox hbBotones = new HBox(btnCancelar, btnModificar);
         hbBotones.setSpacing(10);
        
         GridPane gpPrincipal = new GridPane();
         gpPrincipal.setPadding(new Insets(5, 5, 5, 5));
         gpPrincipal.setHgap(10);
         gpPrincipal.setVgap(10);
         
         gpPrincipal.add(lbIdNotaRem, 0, 0);
         gpPrincipal.add(tfIdNotaRem, 1, 0);
         gpPrincipal.add(lbFolio, 0, 1);
         gpPrincipal.add(tfFolio, 1, 1);
         gpPrincipal.add(lbFecha, 0, 2);
         gpPrincipal.add(dpFecha, 1, 2);
         gpPrincipal.add(lbTiOper, 0, 3);
         gpPrincipal.add(cbTiOper, 1, 3);
         gpPrincipal.add(lbMonto, 0, 4);
         gpPrincipal.add(tfMonto, 1, 4);
         gpPrincipal.add(hbBotones, 1, 5);
        
         vbPpal.getChildren().addAll(lbTituloVista, tvNotasRemision, gpPrincipal);
        return vbPpal;
    }
    private VBox vistaGenerarTraspaso(){
        Label lbTituloVista = new Label("TRASPASO PRODUCTOS ENTRE TIENDAS");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);     
         if (lstTrasp.size()>0){
            lstTrasp = FXCollections.observableArrayList();
        }
         if (lstdetrasp.size()>0){
            lstdetrasp = FXCollections.observableArrayList();
        }
        
        VBox vbVistaPpal = new VBox();
        
        //Etiquetas/datos de la Remisión
        //Label lbFolio = new Label("Folio de Nota: ");
        Label lbFecha = new Label("Fecha: ");
        
        //TextField tfFolio = new TextField();
        DatePicker dpFecha = new DatePicker(LocalDate.now());
        
	Label lbCantidad  = new Label("Cantidad");
	Label lbCodigoProd  = new Label("Codigo Producto: ");
        Label lbDescProducto = new Label ("Descripción Producto: ");
        
        TextField tfCantidad = new TextField();
        tfCantidad.setMaxWidth(80);
        
        TextField tfCodigoProducto = new TextField();
        TextField tfDescrProd = new TextField();
        tfDescrProd.setMaxWidth(300);
        tfDescrProd.setPrefWidth(300);
        
        //Etiquetas/Datos de Traspaso;
        Label lbTiendaTraspaso  = new Label("Tienda Traspaso: ");
	Label lbResponsable_TiendaEnvia  = new Label("Resp. Envio: ");
	Label lbResponsable_TiendaRecive  = new Label("Resp. Recibe: ");
	Label lbResponsable_Transporta  = new Label("Resp. Transporta: ");
        TextField tfTiendaTraspaso  = new TextField();
	TextField tfResponsable_TiendaEnvia  = new TextField() {
            @Override public void replaceText(int start, int end, String text) {
              super.replaceText(start, end, text.toUpperCase());
            }
        };
        /* tfResponsable_TiendaEnvia.setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));*/
	TextField tfResponsable_TiendaRecive  = new TextField();
	TextField tfResponsable_Transporta  = new TextField();
        
        //Componentes de Interfaz
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgBusquedas = new ToggleGroup();
        
        RadioButton rbTodos = new RadioButton("Todos");
        RadioButton rbCodigo = new RadioButton("Codigo");
        RadioButton rbDescripcion = new RadioButton("Descripción");
        RadioButton rbCategoria = new RadioButton("Categoria");
        
        rbTodos.setSelected(true);
        
        rbTodos.setToggleGroup(tgBusquedas);
        rbCodigo.setToggleGroup(tgBusquedas);
        rbDescripcion.setToggleGroup(tgBusquedas);
        rbCategoria.setToggleGroup(tgBusquedas);
        
        
       List<String> lstCategorias = new ArrayList<>();
       List<String> lstWherecat = new ArrayList<>();
       lstWherecat.add("id_categoria is not null");
       for (categoria i : categDAO.consultarCategoria(lstWherecat)){
            lstCategorias.add(i.getCategoria());
       }
        
        Label lbCodigo = new Label("Codigo: ");
        TextField tfCodigo = new TextField();
        Label lbDescripcion = new Label("Descripción: ");
        TextField tfDescripcion = new TextField();
        Label lbCategoria = new Label("Categoria: ");
        ComboBox cbCategoria = new ComboBox(FXCollections.observableArrayList(lstCategorias));
        cbCategoria.setPrefWidth(140);
        
        Label lbInventario = new Label("Tabla Inventario: ");
        TableView tvInventario = new TableView();
        tvInventario.setPrefHeight(350);
        tvInventario.setPrefWidth(550);
        
        TableColumn<inventario, Integer> claveProdColumna = new TableColumn<>("Codigo Producto");
        claveProdColumna.setMinWidth(120);
        claveProdColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));

        TableColumn<inventario, Integer> existenciaColumna = new TableColumn<>("Existencia");
        existenciaColumna.setMinWidth(120);
        existenciaColumna.setCellValueFactory(new PropertyValueFactory<>("existencia"));
        
        TableColumn<inventario, Integer> idUbicacionColumna = new TableColumn<>("Id Ubicación");
        idUbicacionColumna.setMinWidth(120);
        idUbicacionColumna.setCellValueFactory(new PropertyValueFactory<>("id_ubicacion"));
        
        TableColumn<inventario, Float> pMenudeoColumna = new TableColumn<>("Precio Menudeo");
        pMenudeoColumna.setMinWidth(120);
        pMenudeoColumna.setCellValueFactory(new PropertyValueFactory<>("precio_menudeo"));        

        TableColumn<inventario, Float> pMayoreoColumna = new TableColumn<>("Precio Mayoreo");
        pMayoreoColumna.setMinWidth(120);
        pMayoreoColumna.setCellValueFactory(new PropertyValueFactory<>("precio_mayoreo"));
        
        TableColumn<inventario, String> descripcionColumna = new TableColumn<>("Descripción");
        descripcionColumna.setMinWidth(120);
        descripcionColumna.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        
        TableColumn<inventario, String> uMedidaColumna = new TableColumn<>("Unidad Medidad");
        uMedidaColumna.setMinWidth(120);
        uMedidaColumna.setCellValueFactory(new PropertyValueFactory<>("unidad_medida"));

        TableColumn<inventario, Float> cCompraColumna = new TableColumn<>("Costo Compra");
        cCompraColumna.setMinWidth(120);
        cCompraColumna.setCellValueFactory(new PropertyValueFactory<>("costo_compra"));
        
        TableColumn<inventario, Integer> codProvColumna = new TableColumn<>("Codigo Proveedor");
        codProvColumna.setMinWidth(120);
        codProvColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prov"));
        
        tvInventario.getColumns().addAll(claveProdColumna, descripcionColumna, existenciaColumna,
                pMenudeoColumna, pMayoreoColumna, uMedidaColumna, idUbicacionColumna, 
                codProvColumna, cCompraColumna);
        List<String> lstWhere = new ArrayList<>();
        lstWhere.add("codigo_prod is not null");
        tvInventario.setItems(invent.consultarInventario(lstWhere));
        
        tvInventario.setOnMouseClicked((event) -> {
            inventario inv = new inventario();
            inv = (inventario) tvInventario.getSelectionModel().getSelectedItem();
            tfCodigoProducto.setText(String.valueOf(inv.getCodigo_prod()));
            tfDescrProd.setText(inv.getDescripcion());
        });
        
        
        Label lbProducto = new Label("Tabla Producto Seleccionados: ");
        TableView tvProductosSelecc = new TableView();
        tvProductosSelecc.setPrefHeight(350);
        tvProductosSelecc.setPrefWidth(550);
        
        TableColumn<detalle_traspaso, Integer> codigoProdColumna = new TableColumn<>("Codigo Producto");
        codigoProdColumna.setMinWidth(120);
        codigoProdColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));
        
        TableColumn<detalle_traspaso, String> descrProductColumna = new TableColumn<>("Descripción Producto");
        descrProductColumna.setMinWidth(220);
        descrProductColumna.setCellValueFactory(new PropertyValueFactory<>("descrprod"));
        
        TableColumn<detalle_traspaso, Integer> cantidadProductColumna = new TableColumn<>("Cantidad");
        cantidadProductColumna.setMinWidth(80);
        cantidadProductColumna.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        
        
        tvProductosSelecc.getColumns().addAll(codigoProdColumna, descrProductColumna, cantidadProductColumna);
        tvProductosSelecc.setItems(lstdetrasp);
        
        MenuItem miEliminarDetTraspProduc = new MenuItem("Eliminar");
        miEliminarDetTraspProduc.setOnAction((event) -> {
           tvProductosSelecc.getItems().remove(tvProductosSelecc.getSelectionModel().getSelectedIndex());
        });
        ContextMenu cmProducSelec = new ContextMenu();
        cmProducSelec.getItems().add(miEliminarDetTraspProduc);
        tvProductosSelecc.setContextMenu(cmProducSelec);

        Button btnAgregarProducto = new Button("Agregar Producto");
        btnAgregarProducto.setOnAction((ActionEvent e)->{
            
            inventario inv = new inventario();
            inv = (inventario) tvInventario.getSelectionModel().getSelectedItem();
            if (inv.getExistencia()>= Integer.parseInt(tfCantidad.getText())){
                detalle_traspaso detrasp = new detalle_traspaso();
                detrasp.setCodigo_prod(Integer.parseInt(tfCodigoProducto.getText()));
                detrasp.setDescrprod(tfDescrProd.getText());
                detrasp.setCantidad(Integer.parseInt(tfCantidad.getText()));
                lstdetrasp.add(detrasp);
            }else {
                Alert MensajeError = new Alert(Alert.AlertType.ERROR);
                MensajeError.setTitle("Error");
                MensajeError.setContentText("No puedes poner una cantidad mayor a \n la de la existencia en inventario");
                MensajeError.show();
            }
        });
        
        
        Button btnSeleccionarCliente = new Button("Seleccionar Cliente..");
        btnSeleccionarCliente.setOnAction((ActionEvent e)->{
            ventanaSeleccionarClientes();
            
        });
        
        GridPane gpClienteSeleccionado = new GridPane();
        gpClienteSeleccionado.setPadding(new Insets(5, 5, 5, 5));
        gpClienteSeleccionado.setVgap(10);
        gpClienteSeleccionado.setHgap(10);
        
        gpClienteSeleccionado.add(lbTiendaTraspaso, 0, 0);
        gpClienteSeleccionado.add(tfTiendaTraspaso, 1, 0);
        gpClienteSeleccionado.add(lbResponsable_TiendaRecive, 2, 0);
        gpClienteSeleccionado.add(tfResponsable_TiendaRecive, 3, 0);
        
        gpClienteSeleccionado.add(lbResponsable_TiendaEnvia, 0, 1);
        gpClienteSeleccionado.add(tfResponsable_TiendaEnvia, 1, 1);
        gpClienteSeleccionado.add(lbResponsable_Transporta, 2, 1);
        gpClienteSeleccionado.add(tfResponsable_Transporta, 3, 1);
        
        
        VBox vbHead = new VBox();

        VBox vbClienteSeleccion = new VBox();
        vbClienteSeleccion.setSpacing(10);
        vbClienteSeleccion.getChildren().addAll(btnSeleccionarCliente, gpClienteSeleccionado);
        
        HBox hbTipoSeleccion = new HBox();
        hbTipoSeleccion.getChildren().addAll(rbTodos, rbCodigo, rbDescripcion, rbCategoria);
        hbTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        hbTipoSeleccion.setSpacing(5);
        
        VBox vbCodigo = new VBox();
        vbCodigo.getChildren().addAll(lbCodigo, tfCodigo);
        vbCodigo.setSpacing(5);
        
        VBox vbDesc = new VBox();
        vbDesc.getChildren().addAll(lbDescripcion, tfDescripcion);
        vbDesc.setSpacing(5);
        
        VBox vbCat = new VBox();
        vbCat.getChildren().addAll(lbCategoria, cbCategoria);
        vbCat.setSpacing(5);
        
        
        HBox hbCompSeleccion = new HBox();
        Button btnBuscarProductos = new Button("Seleccionar");
        btnBuscarProductos.setMaxHeight(50);
        btnBuscarProductos.setOnAction((ActionEvent e)->{
         List<inventario> lstInv = new ArrayList<>();
         List<String> lstWherelc = new ArrayList<>();
         
          if (rbTodos.isSelected()){
           lstWherelc.clear();
           lstWherelc.add("codigo_prod is not null");
           tvInventario.setItems(invent.consultarInventario(lstWherelc));
           //String titulo = "MODIFICAR PRODUCTOS ("+String.valueOf(tvInventario.getItems().size())+" Seleccionados)";
         } 
         
         if (rbCodigo.isSelected()){
           lstWherelc.clear();
           lstWhere.add("codigo_prod = "+tfCodigo.getText());
           //lstInv=invent.consultarInventario(lstWhere);
           //ObservableList obList = FXCollections.observableArrayList(lstInv);
           tvInventario.setItems(invent.consultarInventario(lstWhere));
         }
         if (rbDescripcion.isSelected()){
           lstWherelc.clear();
           //lstWherelc.add(tfDescripcion.getText());
           lstWherelc.add("descripcion like '%"+tfDescripcion.getText()+"%'");
           //lstInv=invent.consultarInventario(lstWhere);
           //ObservableList obList = FXCollections.observableArrayList(lstInv);
           tvInventario.setItems(invent.consultarInventario(lstWherelc));
         }
         
         if (rbCategoria.isSelected()){
           lstWherelc.clear();
           lstWherecat.add("id_categoria is not null");  
           for (categoria i : categDAO.consultarCategoria(lstWherecat)){
             String strCategoria =  i.getCategoria();
             if (strCategoria.compareTo(cbCategoria.getSelectionModel().getSelectedItem().toString())==0){
                  lstWhere.add("id_categoria = "+i.getId_categoria());
                  tvInventario.setItems(invent.consultarInventario(lstWhere));
             }
           }
         }
        });
        
        
        Button btnGeneraracsv = new Button("Generar CVS");
        btnGeneraracsv.setOnAction((event) -> {
            List<String[]> lstReg = new ArrayList<>();
            for  (detalle_traspaso d: lstdetrasp){
               String[] s = {"", "", "", ""};
               s[0]= String.valueOf(d.getCodigo_prod());
               s[1]= d.getDescrprod();
               s[2]= String.valueOf(d.getCantidad());
               lstReg.add(s);
            }
            LocalDateTime ldT = LocalDateTime.now();
            String strF= String.valueOf(ldT.getYear())+
                    String.valueOf(ldT.getMonthValue())+
                    String.valueOf(ldT.getDayOfMonth())+
                    String.valueOf(ldT.getHour())+
                    String.valueOf(ldT.getMinute())+
                    String.valueOf(ldT.getSecond());
            String archCSV = "Reportes/Traspaso/Traspaso"+strF+".csv";
                  CSVWriter writer;
            try {
                writer = new CSVWriter(new FileWriter(archCSV));
                writer.writeAll(lstReg);
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(CRMPLAMAR.class.getName()).log(Level.SEVERE, null, ex);
            }
            Alert msjAlert = new Alert(Alert.AlertType.INFORMATION);
            msjAlert.setTitle("Informacion-traspaso");
            msjAlert.setContentText("Archivo CSV para Carga Generado");
            msjAlert.show();
        });
        
        hbCompSeleccion.getChildren().addAll(vbCodigo, vbDesc, vbCat, btnBuscarProductos);
        hbCompSeleccion.setPadding(new Insets(10, 10, 10, 10));
        hbCompSeleccion.setSpacing(5);

        Separator spSeleccionProductos = new Separator();
        vbHead.getChildren().addAll(lbTipoBusqueda, hbTipoSeleccion, hbCompSeleccion, spSeleccionProductos);
        
        VBox vbTabInventario = new VBox();
        vbTabInventario.setPadding(new Insets(5, 5, 5, 5));
        
        vbTabInventario.getChildren().addAll(lbInventario, tvInventario, lbProducto, tvProductosSelecc);
        
        Separator spVenta = new Separator();
        Separator spProducto =  new Separator();
        
        GridPane gpBloqueVenta = new GridPane();
        gpBloqueVenta.setPadding(new Insets(5, 5, 5, 5));
        gpBloqueVenta.setVgap(10);
        gpBloqueVenta.setHgap(10);
        
        gpBloqueVenta.add(lbFecha, 0, 0);
        gpBloqueVenta.add(dpFecha , 1, 0);

        //gpBloqueVenta.add(lbFolio , 0, 1);
        //gpBloqueVenta.add(tfFolio , 1, 1);

        GridPane gpBloqueProducto = new GridPane();
        gpBloqueProducto.setPadding(new Insets(5, 5, 5, 5));
        gpBloqueProducto.setVgap(10);
        gpBloqueProducto.setHgap(10);
        
        gpBloqueProducto.add(lbCodigoProd , 0, 0);
        gpBloqueProducto.add(tfCodigoProducto , 1, 0);
        
        gpBloqueProducto.add(lbCantidad , 2, 0);
        gpBloqueProducto.add(tfCantidad , 3, 0);
        
        gpBloqueProducto.add(lbDescProducto , 2, 1);
        gpBloqueProducto.add(tfDescrProd , 3, 1);
        
        gpBloqueProducto.add(btnAgregarProducto, 2, 4);

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction((event) -> {
            traspaso trasTemp = new traspaso();
            trasTemp.setTienda_traspaso(tfTiendaTraspaso.getText());
            trasTemp.setResponsable_tienda_recibe(tfResponsable_TiendaRecive.getText());
            trasTemp.setResponsable_tienda_envia(tfResponsable_TiendaEnvia.getText());
            trasTemp.setResponsable_transportar_prod(tfResponsable_Transporta.getText());
            trasTemp.setFecha(dpFecha.getValue().toString());
            int idTraspaso = traspDAO.insertarTraspaso(trasTemp);
            //List<detalle_venta> detalleVentatList = detventa.subList(0, detventa.size());
            for (detalle_traspaso detrasTemp: lstdetrasp){
                detrasTemp.setId_traspaso(idTraspaso);
                detraspDAO.insertarDetTraspaso(detrasTemp);
            } 
            Alert msjAlert = new Alert(Alert.AlertType.INFORMATION);
            msjAlert.setTitle("Informacion-traspaso");
            msjAlert.setContentText("Informacion de Traspaso Registrada");
            msjAlert.show();
            
        });
        Button btnGenerarPDF = new Button("Generar PDF");
        btnGenerarPDF.setOnAction((event) -> { 
        try{
            
           /* User home directory location */
            //String userHomeDirectory = System.getProperty("user.home");
            /* Output file location */
            
            File file;
            JasperReport jasperReport;
            file = new File("Reportes/Formatos/traspaso.jasper");
            jasperReport = (JasperReport) JRLoader.loadObject(file);
            LocalDateTime ld = LocalDateTime.now();
            String fechaFile = String.valueOf(ld.getDayOfMonth())+String.valueOf(ld.getMonth())+String.valueOf(ld.getYear())+String.valueOf(ld.getHour())+String.valueOf(ld.getMinute())+String.valueOf(ld.getSecond());
            //String outputFile = userHomeDirectory + File.separatorChar + "TRASPASO"+fechaFile+".pdf";
            String outputFile = "Reportes/Traspaso/" + File.separatorChar + "TRASPASO"+fechaFile+".pdf";
           Map<String, Object> parameters = new HashMap<>();
           JRBeanCollectionDataSource itemsProSelec = new JRBeanCollectionDataSource(tvProductosSelecc.getItems());
           List<String> listWhere = new ArrayList<>();
           listWhere.add("id_suc = 1");
           List<SUCURSAL> sucList = sucDAO.consultaSucursal(listWhere);
           SUCURSAL sucIdent = sucList.get(0);
           parameters.put("itemsProSelec", itemsProSelec);
           parameters.put("tiendaTraspaso", tfTiendaTraspaso.getText());
           parameters.put("respRecibe", tfResponsable_TiendaRecive.getText());
           parameters.put("respEnvia", tfResponsable_TiendaEnvia.getText());
           parameters.put("respTrasp", tfResponsable_Transporta.getText());
           parameters.put("Sucursal", sucIdent.getNombre());
           parameters.put("Fecha", dpFecha.getValue().toString());
           
           /* Generando el PDF */
            //C:\Users\dopcan\Documents\NetBeansProjects\ClasesConsultorio\src\gestionconsultorio
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            /* outputStream to create PDF */
            OutputStream outputStream = new FileOutputStream(new File(outputFile));
            /* Write content to PDF file */
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            outputStream.close();
              Alert resp = new Alert(Alert.AlertType.INFORMATION);
              resp.setTitle("Informacion");
              resp.setContentText("Reporte Generado en Carpeta Reportes/Traspaso!! ");
              resp.showAndWait();
            
        } catch (JRException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
                 Logger.getLogger(CRMPLAMAR.class.getName()).log(Level.SEVERE, null, ex);
             }            
        });      
        HBox hbBotonesInferiores = new HBox();
        hbBotonesInferiores.setAlignment(Pos.CENTER_RIGHT);
        hbBotonesInferiores.getChildren().addAll(btnCancelar, btnGuardar, btnGenerarPDF, btnGeneraracsv);
        
        VBox vbTabProducto = new VBox();
        vbTabProducto.setSpacing(5);
        vbTabProducto.getChildren().addAll(vbClienteSeleccion, spVenta, gpBloqueVenta, spProducto, gpBloqueProducto, hbBotonesInferiores);
        
        
        HBox hbBody = new HBox();
        hbBody.getChildren().addAll(vbTabInventario, vbTabProducto);
        
        vbVistaPpal.getChildren().addAll(lbTituloVista, vbHead,hbBody);
        
        return vbVistaPpal; 
    }
    private VBox vistaNuevoProducto(){
        if (detventa.size()>0){
            detventa = FXCollections.observableArrayList();
        }

       List<String> lstProveedores = new ArrayList<>();
       lstProveedores.add("");
       List<String> lstWherecat = new ArrayList<>();
       lstWherecat.add("codigo_prov is not null");
       for (PROVEEDOR i : provDAO.consultaProveedores(lstWherecat)){
            lstProveedores.add(i.getNombre());
       }       
        
        VBox vbPpal= new VBox();
        vbPpal.setAlignment(Pos.CENTER);
        
        Label lbTituloVista = new Label("AGREGAR PRODUCTO");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
	Label lbExistencia = new Label("Existencias: ");
	Label lbId_ubicacion = new Label("Ubicación: ");
	Label lbPrecio_menudeo = new Label("Precio Menudeo: ");
	Label lbPrecio_mayoreo = new Label("Precio Mayoreo: ");
	Label lbDescripcion = new Label("Descripción: ");
	Label lbUnidad_medida = new Label("Unidad Medida: ");
	Label lbCosto_compra = new Label("Costo Compra");
	Label lbCodigo_prov = new Label("Código Proveedor: ");

	TextField tfExistencia = new TextField();
        tfExistencia.setMaxWidth(80);
	TextField tfId_ubicacion = new TextField();
        tfId_ubicacion.setMaxWidth(80);
	TextField tfPrecio_menudeo = new TextField();
        tfPrecio_menudeo.setMaxWidth(120);
	TextField tfPrecio_mayoreo = new TextField();
        tfPrecio_mayoreo.setMaxWidth(120);
	TextArea tfDescripcion = new TextArea();
        tfDescripcion.setMaxHeight(40);
        tfDescripcion.setMaxWidth(280);
        ComboBox cbProveedores = new ComboBox(FXCollections.observableArrayList(lstProveedores));        

        ComboBox tfUnidad_medida = new ComboBox();
        ObservableList<String> Options = FXCollections.observableArrayList(
           "PZA.",
           "Mts.",
           "Unidad",
           "Kg",
           "grm",
           "PQT."
        );
        tfUnidad_medida.setItems(Options);
        
        tfUnidad_medida.setMaxWidth(120);
	TextField tfCosto_compra = new TextField();
        tfCosto_compra.setMaxWidth(120);
	TextField tfCodigo_prov = new TextField();
        tfCodigo_prov.setMaxWidth(120);
	cbProveedores.setOnAction((event) -> {
            int codProv = provDAO.ObtenerIdProveedor(cbProveedores.getValue().toString());
            tfCodigo_prov.setText(String.valueOf(codProv));
        });        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction((ActionEvent e)->{
           inventarioDAO invDAO = new inventarioDAO();
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setHeaderText(null);
           alert.setTitle("Confirmación");
           alert.setContentText("¿Estas seguro de confirmar la acción?");
           Optional<ButtonType> action = alert.showAndWait(); 
           if (action.get() == ButtonType.OK) {
              invDAO.insertarProducto(Integer.parseInt(tfExistencia.getText()), Integer.parseInt(tfId_ubicacion.getText()), Float.parseFloat(tfPrecio_menudeo.getText()), Float.parseFloat(tfPrecio_mayoreo.getText()), tfDescripcion.getText(), tfUnidad_medida.getValue().toString(), Float.parseFloat(tfCosto_compra.getText()), Integer.parseInt(tfCodigo_prov.getText()));
              removerVistas();
           } else {
              removerVistas();
           }
        });
        
        HBox hbProveedores = new HBox();
        hbProveedores.setSpacing(10);
        hbProveedores.getChildren().addAll(tfCodigo_prov, cbProveedores);
        
        HBox hbBotones = new HBox();
        hbBotones.setAlignment(Pos.CENTER_RIGHT);
        hbBotones.getChildren().addAll(btnCancelar, btnGuardar);        

        GridPane gpPpal = new GridPane();
        gpPpal.setPadding(new Insets(5,5,5,5));
        gpPpal.setVgap(5);
        gpPpal.setHgap(5);
        
        gpPpal.add(lbDescripcion, 2, 0);
        gpPpal.add(tfDescripcion, 3, 0);
        gpPpal.add(lbCosto_compra, 0, 1);
        gpPpal.add(tfCosto_compra, 1, 1);
        gpPpal.add(lbCodigo_prov, 2, 1);
        gpPpal.add(hbProveedores, 3, 1);
        gpPpal.add(lbUnidad_medida, 0, 2);
        gpPpal.add(tfUnidad_medida, 1, 2);
        gpPpal.add(lbExistencia, 2, 2);
        gpPpal.add(tfExistencia, 3, 2);       
        gpPpal.add(lbPrecio_menudeo, 0, 3);
        gpPpal.add(tfPrecio_menudeo, 1, 3);
        gpPpal.add(lbPrecio_mayoreo, 2, 3);
        gpPpal.add(tfPrecio_mayoreo, 3, 3);
        gpPpal.add(lbId_ubicacion, 0, 4);
        gpPpal.add(tfId_ubicacion, 1, 4);
        gpPpal.add(hbBotones, 3, 4);
        gpPpal.setAlignment(Pos.CENTER);
        vbPpal.getChildren().addAll(lbTituloVista, gpPpal);
        return vbPpal;
    
    }
    private VBox vistaModificarProducto(){
        VBox vbPpal= new VBox();
        int totalProductos = 0;
        Label lbTituloVista = new Label("MODIFICAR PRODUCTOS");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        Label lbTablaProducto = new Label("Productos");

        TableView<inventario> tvProductos = new TableView();
        
         List<String> lstWhere = new ArrayList<>();
         lstWhere.add("codigo_prod is not null");
         
       List<String> lstCategorias = new ArrayList<>();
       lstCategorias.add("");
       List<String> lstWherecat = new ArrayList<>();
       lstWherecat.add("id_categoria is not null");
       for (categoria i : categDAO.consultarCategoria(lstWherecat)){
            lstCategorias.add(i.getCategoria());
       }
        
        //Componentes de Interfaz--------------------------------------------------------------
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgBusquedas = new ToggleGroup();
        
        RadioButton rbCodigo = new RadioButton("Codigo");
        RadioButton rbDescripcion = new RadioButton("Descripcion");
        RadioButton rbCategoria = new RadioButton("Categoria");
        RadioButton rbTodos = new RadioButton("Todos");
        rbCodigo.setToggleGroup(tgBusquedas);
        rbDescripcion.setToggleGroup(tgBusquedas);
        rbCategoria.setToggleGroup(tgBusquedas);        
        rbTodos.setToggleGroup(tgBusquedas); 
        rbTodos.setSelected(true);
        
        Label lbCodigo = new Label("Codigo: ");
        TextField tfCodigo = new TextField();
        Label lbBusquedaCampo = new Label("Por Descripcion: ");
        TextField tfBusquedaDescripcion = new TextField();
        Label lbCategoria = new Label("Categoria: ");
        ComboBox cbCategoria = new ComboBox(FXCollections.observableList(lstCategorias));
        cbCategoria.setPrefWidth(140);        
        
        HBox hbCompSeleccion = new HBox();
        Button btnBuscarProductos = new Button("Seleccionar");
        btnBuscarProductos.setMaxHeight(50);
        btnBuscarProductos.setOnAction((ActionEvent e)->{
         
         if (rbTodos.isSelected()){
           lstWhere.add("codigo_prod is not null");
           tvProductos.setItems(invent.consultarInventario(lstWhere));
           //String titulo = "MODIFICAR PRODUCTOS ("+String.valueOf(tvProductos.getItems().size())+" Seleccionados)";
           //lbTituloVista.setText(titulo);
         }    
            
         if (rbCodigo.isSelected()){
           lstWhere.add("codigo_prod = "+tfCodigo.getText());
           tvProductos.setItems(invent.consultarInventario(lstWhere));
         }
         
         if (rbDescripcion.isSelected()){
           lstWhere.add("descripcion like '%"+tfBusquedaDescripcion.getText()+"%'");
           tvProductos.setItems(invent.consultarInventario(lstWhere));
         }
         
         if (rbCategoria.isSelected()){
             String strCat = cbCategoria.getValue().toString();
           if (strCat.compareTo("")==0){
                  lstWhere.add("id_categoria = 0");
                  tvProductos.setItems(invent.consultarInventario(lstWhere));
           }else{
              lstWhere.add("id_categoria ='"+categDAO.consultaIdCategoria(strCat)+"' ");
              tvProductos.setItems(invent.consultarInventario(lstWhere));
           }
         }
           String titulo = "MODIFICAR PRODUCTOS ("+String.valueOf(tvProductos.getItems().size())+" Seleccionados)";
           lbTituloVista.setText(titulo);       
        });
        
        VBox vbCodigo = new VBox();
        VBox vbDesc = new VBox();
        VBox vbCat = new VBox();
        vbCodigo.getChildren().addAll(lbCodigo, tfCodigo);
        vbDesc.getChildren().addAll(lbBusquedaCampo, tfBusquedaDescripcion);
        vbCat.getChildren().addAll(lbCategoria, cbCategoria);
        
        
        hbCompSeleccion.getChildren().addAll(vbCodigo, vbDesc, vbCat, btnBuscarProductos);
        hbCompSeleccion.setPadding(new Insets(10, 10, 10, 10));
        hbCompSeleccion.setSpacing(5);
        
        HBox hbTipoSeleccion = new HBox();
        hbTipoSeleccion.getChildren().addAll(rbTodos, rbCodigo, rbDescripcion, rbCategoria);
        hbTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        hbTipoSeleccion.setSpacing(5);

        tvProductos.setMaxHeight(320);

        TableColumn<inventario, Integer> claveProdColumna = new TableColumn<>("Codigo Producto");
        claveProdColumna.setMinWidth(120);
        claveProdColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));

        TableColumn<inventario, Integer> existenciaColumna = new TableColumn<>("Existencia");
        existenciaColumna.setMinWidth(120);
        existenciaColumna.setCellValueFactory(new PropertyValueFactory<>("existencia"));
        
        TableColumn<inventario, Integer> idUbicacionColumna = new TableColumn<>("Id Ubicación");
        idUbicacionColumna.setMinWidth(120);
        idUbicacionColumna.setCellValueFactory(new PropertyValueFactory<>("id_ubicacion"));
        
        TableColumn<inventario, Float> pMenudeoColumna = new TableColumn<>("Precio Menudeo");
        pMenudeoColumna.setMinWidth(120);
        pMenudeoColumna.setCellValueFactory(new PropertyValueFactory<>("precio_menudeo"));        

        TableColumn<inventario, Float> pMayoreoColumna = new TableColumn<>("Precio Mayoreo");
        pMayoreoColumna.setMinWidth(120);
        pMayoreoColumna.setCellValueFactory(new PropertyValueFactory<>("precio_mayoreo"));
        
        TableColumn<inventario, String> descripcionColumna = new TableColumn<>("Descripción");
        descripcionColumna.setMinWidth(120);
        descripcionColumna.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        
        TableColumn<inventario, String> uMedidaColumna = new TableColumn<>("Unidad Medidad");
        uMedidaColumna.setMinWidth(120);
        uMedidaColumna.setCellValueFactory(new PropertyValueFactory<>("unidad_medida"));

        TableColumn<inventario, Float> cCompraColumna = new TableColumn<>("Costo Compra");
        cCompraColumna.setMinWidth(120);
        cCompraColumna.setCellValueFactory(new PropertyValueFactory<>("costo_compra"));
        
        TableColumn<inventario, Integer> codProvColumna = new TableColumn<>("Codigo Proveedor");
        codProvColumna.setMinWidth(120);
        codProvColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prov"));
        
        TableColumn<inventario, Integer> idCategoriaColumna = new TableColumn<>("id Categoria");
        idCategoriaColumna.setMinWidth(120);
        idCategoriaColumna.setCellValueFactory(new PropertyValueFactory<>("id_categoria"));

        tvProductos.getColumns().addAll(claveProdColumna, existenciaColumna, idUbicacionColumna,
                pMenudeoColumna, pMayoreoColumna, descripcionColumna, uMedidaColumna, 
                cCompraColumna, codProvColumna, idCategoriaColumna);
        

         tvProductos.setItems(invent.consultarInventario(lstWhere));
         String titulo = lbTituloVista.getText()+" ("+String.valueOf(tvProductos.getItems().size())+" Seleccionados)";
         lbTituloVista.setText(titulo);
        
        Label lbCodigo_prod = new Label("Codigo Producto: ");
	Label lbExistencia = new Label("Existencias: ");
	Label lbId_ubicacion = new Label("Ubicación: ");
	Label lbPrecio_menudeo = new Label("Precio Menudeo: ");
	Label lbPrecio_mayoreo = new Label("Precio Mayoreo: ");
	Label lbDescripcion = new Label("Descripción: ");
	Label lbUnidad_medida = new Label("Unidad Medida: ");
	Label lbCosto_compra = new Label("Costo Compra");
	Label lbCodigo_prov = new Label("Código Proveedor: ");
	Label lbCategoriaMod = new Label("Categoria: ");

        TextField tfCodigo_prod = new TextField();
        tfCodigo_prod.setMaxWidth(80);
	TextField tfExistencia = new TextField();
        tfExistencia.setMaxWidth(80);
	TextField tfId_ubicacion = new TextField();
        tfId_ubicacion.setMaxWidth(80);
	TextField tfPrecio_menudeo = new TextField();
        tfPrecio_menudeo.setMaxWidth(120);
	TextField tfPrecio_mayoreo = new TextField();
        tfPrecio_mayoreo.setMaxWidth(120);
	TextArea tfDescripcion = new TextArea();
        tfDescripcion.setMaxHeight(40);
        tfDescripcion.setMaxWidth(280);
        ObservableList<String> options = FXCollections.observableArrayList(
        "PZA.",
        "Mts.",
        "Unidad",
        "Kg",
        "grm",
        "PQT."
        );
	ComboBox tfUnidad_medida = new ComboBox(options);
        ComboBox cbCategoriaMod = new ComboBox(FXCollections.observableList(lstCategorias));
        cbCategoriaMod.setPrefWidth(140); 
        
        tfUnidad_medida.setMaxWidth(120);
	TextField tfCosto_compra = new TextField();
        tfCosto_compra.setMaxWidth(120);
	TextField tfCodigo_prov = new TextField();
        tfCodigo_prov.setMaxWidth(120);
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        
        tvProductos.setOnMouseClicked((event) -> {
            inventario inv= (inventario) tvProductos.getSelectionModel().getSelectedItem();
            tfDescripcion.setText(inv.getDescripcion());
            tfCodigo_prod.setText(String.valueOf(inv.getCodigo_prod()));
            tfCosto_compra.setText(String.valueOf(inv.getCosto_compra()));
            tfCodigo_prov.setText(String.valueOf(inv.getCodigo_prov()));
            tfExistencia.setText(String.valueOf(inv.getExistencia()));
            tfPrecio_menudeo.setText(String.valueOf(inv.getPrecio_menudeo()));
            tfPrecio_mayoreo.setText(String.valueOf(inv.getPrecio_mayoreo()));
            tfId_ubicacion.setText(String.valueOf(inv.getId_ubicacion()));
            tfUnidad_medida.setValue(inv.getUnidad_medida());
            
            if (inv.getId_categoria()==0){
               cbCategoriaMod.getSelectionModel().select(0); 
            }else {
                String strCategoria = categDAO.consultarCategoria(inv.getId_categoria());
                cbCategoriaMod.setValue(strCategoria);
            }

        });
        Button btnModificar = new Button("Modificar");
        btnModificar.setOnAction((ActionEvent e)->{
           inventarioDAO invDAO = new inventarioDAO();
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setHeaderText(null);
           alert.setTitle("Confirmación");
           alert.setContentText("¿Estas seguro de confirmar la acción?");
           Optional<ButtonType> action = alert.showAndWait(); 
           if (action.get() == ButtonType.OK) {
              inventario tempInv = (inventario) tvProductos.getSelectionModel().getSelectedItem();
              int intCategoria=tempInv.getId_categoria();
              if (cbCategoriaMod.getSelectionModel().getSelectedItem().toString().length()==0){
               intCategoria = 0;
              }else {
                  intCategoria = categDAO.consultaIdCategoria(cbCategoriaMod.getSelectionModel().getSelectedItem().toString());
                  System.out.println("Id Categoria: "+ intCategoria);
              }
              invDAO.modificarProducto(Integer.parseInt(tfCodigo_prod.getText()), Integer.parseInt(tfExistencia.getText()), 
                      Integer.parseInt(tfId_ubicacion.getText()), Float.parseFloat(tfPrecio_menudeo.getText()), 
                      Float.parseFloat(tfPrecio_mayoreo.getText()), tfDescripcion.getText(), 
                      tfUnidad_medida.getValue().toString(), Float.parseFloat(tfCosto_compra.getText()), 
                      Integer.parseInt(tfCodigo_prov.getText()), intCategoria);
              Alert resp = new Alert(Alert.AlertType.INFORMATION);
              resp.setTitle("Informacion");
              resp.setContentText("Se actualizaron los datos ");
              resp.showAndWait();
           } 
        });
        HBox hbBotones = new HBox();
        hbBotones.setAlignment(Pos.CENTER_RIGHT);
        hbBotones.getChildren().addAll(btnCancelar, btnModificar);        

        GridPane gpPpal = new GridPane();
        gpPpal.setPadding(new Insets(5,5,5,5));
        gpPpal.setVgap(5);
        gpPpal.setHgap(5);
        
        gpPpal.add(lbCodigo_prod, 0, 0);
        gpPpal.add(tfCodigo_prod, 1, 0);
        gpPpal.add(lbDescripcion, 2, 0);
        gpPpal.add(tfDescripcion, 3, 0);
        gpPpal.add(lbCosto_compra, 0, 1);
        gpPpal.add(tfCosto_compra, 1, 1);
        gpPpal.add(lbCodigo_prov, 2, 1);
        gpPpal.add(tfCodigo_prov, 3, 1);
        gpPpal.add(lbUnidad_medida, 0, 2);
        gpPpal.add(tfUnidad_medida, 1, 2);
        gpPpal.add(lbExistencia, 2, 2);
        gpPpal.add(tfExistencia, 3, 2);       
        gpPpal.add(lbPrecio_menudeo, 0, 3);
        gpPpal.add(tfPrecio_menudeo, 1, 3);
        gpPpal.add(lbPrecio_mayoreo, 2, 3);
        gpPpal.add(tfPrecio_mayoreo, 3, 3);
        gpPpal.add(lbId_ubicacion, 0, 4);
        gpPpal.add(tfId_ubicacion, 1, 4);
        gpPpal.add(lbCategoriaMod, 2, 4);
        gpPpal.add(cbCategoriaMod, 3, 4);
        gpPpal.add(hbBotones, 3, 5);
        vbPpal.getChildren().addAll(lbTituloVista, lbTipoBusqueda, hbTipoSeleccion, hbCompSeleccion, tvProductos, gpPpal);
        return vbPpal;
    
    }
    private VBox vistaReportesProducto(){
        VBox vbPpal= new VBox();
        int totalProductos = 0;
        Label lbTituloVista = new Label("REPORTES DE INVENTARIO");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        Label lbTablaProducto = new Label("Productos");

        TableView<inventario> tvProductos = new TableView();
        
         List<String> lstWhere = new ArrayList<>();
         lstWhere.add("codigo_prod is not null");
         
       List<String> lstCategorias = new ArrayList<>();
       lstCategorias.add("");
       List<String> lstWherecat = new ArrayList<>();
       lstWherecat.add("id_categoria is not null");
       for (categoria i : categDAO.consultarCategoria(lstWherecat)){
            lstCategorias.add(i.getCategoria());
       }
        
        //Componentes de Interfaz--------------------------------------------------------------
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgBusquedas = new ToggleGroup();
        
        RadioButton rbCodigo = new RadioButton("Codigo");
        RadioButton rbDescripcion = new RadioButton("Descripción");
        RadioButton rbCategoria = new RadioButton("Categoria");
        RadioButton rbTodos = new RadioButton("Todos");
        rbCodigo.setToggleGroup(tgBusquedas);
        rbDescripcion.setToggleGroup(tgBusquedas);
        rbCategoria.setToggleGroup(tgBusquedas);        
        rbTodos.setToggleGroup(tgBusquedas); 
        rbTodos.setSelected(true);
        
        Label lbCodigo = new Label("Codigo: ");
        TextField tfCodigo = new TextField();
        Label lbBusquedaDescripcion = new Label("Por Descripcion: ");
        TextField tfBusquedaDescripcion = new TextField();
        Label lbCategoria = new Label("Categoria: ");
        ComboBox cbCategoria = new ComboBox();
        lstCategorias.add("");
        cbCategoria.getItems().addAll(FXCollections.observableList(lstCategorias));
        cbCategoria.setPrefWidth(140);       
        
        HBox hbCompSeleccion = new HBox();
        Button btnBuscarProductos = new Button("Seleccionar");
        btnBuscarProductos.setMaxHeight(50);
        btnBuscarProductos.setOnAction((ActionEvent e)->{
         
         if (rbTodos.isSelected()){
           lstWhere.add("codigo_prod is not null");
           tvProductos.setItems(invent.consultarInventario(lstWhere));
         }    
            
         if (rbCodigo.isSelected()){
           lstWhere.add("codigo_prod = "+tfCodigo.getText());
           tvProductos.setItems(invent.consultarInventario(lstWhere));
         }
         
         if (rbDescripcion.isSelected()){
           lstWhere.add("descripcion like '%"+tfBusquedaDescripcion.getText()+"%' ");
           tvProductos.setItems(invent.consultarInventario(lstWhere));
         }
         
         if (rbCategoria.isSelected()){
           String strCat = cbCategoria.getValue().toString();
           if (strCat.compareTo("")==0){
                  lstWhere.add("id_categoria = 0");
                  tvProductos.setItems(invent.consultarInventario(lstWhere));
           }else{
              lstWhere.add("id_categoria ='"+categDAO.consultaIdCategoria(strCat)+"' ");
              tvProductos.setItems(invent.consultarInventario(lstWhere));
           }
         }

           String titulo = "MODIFICAR PRODUCTOS ("+String.valueOf(tvProductos.getItems().size())+" Seleccionados)";
           lbTituloVista.setText(titulo);
         
        });
        
        VBox vbCodigo = new VBox();
        VBox vbDesc = new VBox();
        VBox vbCat = new VBox();
        vbCodigo.getChildren().addAll(lbCodigo, tfCodigo);
        vbDesc.getChildren().addAll(lbBusquedaDescripcion, tfBusquedaDescripcion);
        vbCat.getChildren().addAll(lbCategoria, cbCategoria);
        
        
        hbCompSeleccion.getChildren().addAll(vbCodigo, vbDesc, vbCat, btnBuscarProductos);
        hbCompSeleccion.setPadding(new Insets(10, 10, 10, 10));
        hbCompSeleccion.setSpacing(5);
        
        HBox hbTipoSeleccion = new HBox();
        hbTipoSeleccion.getChildren().addAll(rbTodos, rbCodigo, rbDescripcion, rbCategoria);
        hbTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        hbTipoSeleccion.setSpacing(5);

        tvProductos.setMaxHeight(320);

        TableColumn<inventario, Integer> claveProdColumna = new TableColumn<>("Codigo Producto");
        claveProdColumna.setMinWidth(120);
        claveProdColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));

        TableColumn<inventario, Integer> existenciaColumna = new TableColumn<>("Existencia");
        existenciaColumna.setMinWidth(120);
        existenciaColumna.setCellValueFactory(new PropertyValueFactory<>("existencia"));
        
        TableColumn<inventario, Integer> idUbicacionColumna = new TableColumn<>("Id Ubicación");
        idUbicacionColumna.setMinWidth(120);
        idUbicacionColumna.setCellValueFactory(new PropertyValueFactory<>("id_ubicacion"));
        
        TableColumn<inventario, Float> pMenudeoColumna = new TableColumn<>("Precio Menudeo");
        pMenudeoColumna.setMinWidth(120);
        pMenudeoColumna.setCellValueFactory(new PropertyValueFactory<>("precio_menudeo"));        

        TableColumn<inventario, Float> pMayoreoColumna = new TableColumn<>("Precio Mayoreo");
        pMayoreoColumna.setMinWidth(120);
        pMayoreoColumna.setCellValueFactory(new PropertyValueFactory<>("precio_mayoreo"));
        
        TableColumn<inventario, String> descripcionColumna = new TableColumn<>("Descripción");
        descripcionColumna.setMinWidth(120);
        descripcionColumna.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        
        TableColumn<inventario, String> uMedidaColumna = new TableColumn<>("Unidad Medidad");
        uMedidaColumna.setMinWidth(120);
        uMedidaColumna.setCellValueFactory(new PropertyValueFactory<>("unidad_medida"));

        TableColumn<inventario, Float> cCompraColumna = new TableColumn<>("Costo Compra");
        cCompraColumna.setMinWidth(120);
        cCompraColumna.setCellValueFactory(new PropertyValueFactory<>("costo_compra"));
        
        TableColumn<inventario, Integer> codProvColumna = new TableColumn<>("Codigo Proveedor");
        codProvColumna.setMinWidth(120);
        codProvColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prov"));
        
        TableColumn<inventario, Integer> idCategoriaColumna = new TableColumn<>("Codigo Proveedor");
        idCategoriaColumna.setMinWidth(120);
        idCategoriaColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prov"));
        
        tvProductos.getColumns().addAll(claveProdColumna, descripcionColumna, existenciaColumna, 
                idUbicacionColumna, pMenudeoColumna, pMayoreoColumna,  uMedidaColumna, 
                cCompraColumna, codProvColumna);
        

         tvProductos.setItems(invent.consultarInventario(lstWhere));
         String titulo = lbTituloVista.getText()+" ("+String.valueOf(tvProductos.getItems().size())+" Seleccionados)";
         lbTituloVista.setText(titulo);
        
        Label lbCodigo_prod = new Label("Codigo Producto: ");
	Label lbExistencia = new Label("Existencias: ");
	Label lbId_ubicacion = new Label("Ubicación: ");
	Label lbPrecio_menudeo = new Label("Precio Menudeo: ");
	Label lbPrecio_mayoreo = new Label("Precio Mayoreo: ");
	Label lbDescripcion = new Label("Descripción: ");
	Label lbUnidad_medida = new Label("Unidad Medida: ");
	Label lbCosto_compra = new Label("Costo Compra");
	Label lbCodigo_prov = new Label("Código Proveedor: ");
	Label lbCategoriaMod = new Label("Categoria: ");

        TextField tfCodigo_prod = new TextField();
        tfCodigo_prod.setMaxWidth(80);
        tfCodigo_prod.setEditable(false);
	TextField tfExistencia = new TextField();
        tfExistencia.setMaxWidth(80);
        tfExistencia.setEditable(false);
	TextField tfId_ubicacion = new TextField();
        tfId_ubicacion.setMaxWidth(80);
        tfId_ubicacion.setEditable(false);
	TextField tfPrecio_menudeo = new TextField();
        tfPrecio_menudeo.setMaxWidth(120);
        tfPrecio_menudeo.setEditable(false);
	TextField tfPrecio_mayoreo = new TextField();
        tfPrecio_mayoreo.setMaxWidth(120);
        tfPrecio_mayoreo.setEditable(false);
	TextArea tfDescripcion = new TextArea();
        tfDescripcion.setMaxHeight(40);
        tfDescripcion.setMaxWidth(280);
        tfDescripcion.setEditable(false);
        ObservableList<String> options = FXCollections.observableArrayList(
        "PZA.", "Mts.", "Unidad",  "Kg", "grm", "PQT."
        );
	ComboBox tfUnidad_medida = new ComboBox(options);
        ComboBox cbCategoriaMod = new ComboBox(FXCollections.observableList(lstCategorias));
        cbCategoriaMod.setPrefWidth(140);
        cbCategoria.setEditable(false);
        
        tfUnidad_medida.setMaxWidth(120);
	TextField tfCosto_compra = new TextField();
        tfCosto_compra.setMaxWidth(120);
        tfCosto_compra.setEditable(false);
	TextField tfCodigo_prov = new TextField();
        tfCodigo_prov.setMaxWidth(120);
        tfCodigo_prov.setEditable(false);
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction((ActionEvent event) -> {
            vbAreaTrabajo.getChildren().remove(0);
        });
        
        tvProductos.setOnMouseClicked((event) -> {
            inventario inv= (inventario) tvProductos.getSelectionModel().getSelectedItem();
            tfDescripcion.setText(inv.getDescripcion());
            tfCodigo_prod.setText(String.valueOf(inv.getCodigo_prod()));
            tfCosto_compra.setText(String.valueOf(inv.getCosto_compra()));
            tfCodigo_prov.setText(String.valueOf(inv.getCodigo_prov()));
            tfExistencia.setText(String.valueOf(inv.getExistencia()));
            tfPrecio_menudeo.setText(String.valueOf(inv.getPrecio_menudeo()));
            tfPrecio_mayoreo.setText(String.valueOf(inv.getPrecio_mayoreo()));
            tfId_ubicacion.setText(String.valueOf(inv.getId_ubicacion()));
            tfUnidad_medida.setValue(inv.getUnidad_medida());
           lstWherecat.add("id_categoria is not null"); 
           
           for (categoria i : categDAO.consultarCategoria(lstWherecat)){
             Integer idCategoria =  i.getId_categoria();
             
             if (idCategoria.compareTo(inv.getId_categoria()) == 0){
                  int optionT = cbCategoriaMod.getItems().size();
                  for (int ii =0; ii<optionT; ii++){
                      String categoriaA = i.getCategoria();
                     if (categoriaA.compareTo(cbCategoriaMod.getItems().get(ii).toString())==0){
                         cbCategoriaMod.getSelectionModel().select(ii);
                     }
                  }
             }
           }
        });
        Button btnReportePdf = new Button("Generar Reporte PDF");
        btnReportePdf.setOnAction((ActionEvent e)->{
         try{
            
           /* User home directory location */
           // String userHomeDirectory = System.getProperty("user.home");
            /* Output file location */
            
            File file;
            JasperReport jasperReport;
            file = new File("Reportes/Formatos/reporteInventario.jasper");
            jasperReport = (JasperReport) JRLoader.loadObject(file);
            LocalDateTime ld = LocalDateTime.now();
            String fechaFile = String.valueOf(ld.getDayOfMonth())+String.valueOf(ld.getMonth())+String.valueOf(ld.getYear())+String.valueOf(ld.getHour())+String.valueOf(ld.getMinute())+String.valueOf(ld.getSecond());
            //String outputFile = userHomeDirectory + File.separatorChar + "ReporteInventario"+fechaFile+".pdf";
            String outputFile = "Reportes/Inventario/" + File.separatorChar + "ReporteInventario"+fechaFile+".pdf";
           //JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tvProductos.getItems().subList(0, tvProductos.getItems().size()-1));
           JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tvProductos.getItems());
           System.out.println("Hay "+tvProductos.getItems().size());
            List<String> listWhere = new ArrayList<>();
            listWhere.add("id_suc = 1");
            List<SUCURSAL> sucList = sucDAO.consultaSucursal(listWhere);
            SUCURSAL sucIdent = sucList.get(0);
           Map<String, Object> parameters = new HashMap<>();
           parameters.put("ItemDataSource", itemsJRBean);
           parameters.put("Sucursal", sucIdent.getNombre());
           parameters.put("Fecha", LocalDate.now().toString());
           
           /* Generando el PDF */
            //C:\Users\dopcan\Documents\NetBeansProjects\ClasesConsultorio\src\gestionconsultorio
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            /* outputStream to create PDF */
            OutputStream outputStream = new FileOutputStream(new File(outputFile));
            /* Write content to PDF file */
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            outputStream.close();
              Alert resp = new Alert(Alert.AlertType.INFORMATION);
              resp.setTitle("Informacion");
              resp.setContentText("Reporte Generado en Carpeta Reportes/Inventario!! ");
              resp.showAndWait();
            
        } catch (JRException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
                 Logger.getLogger(CRMPLAMAR.class.getName()).log(Level.SEVERE, null, ex);
             }
        });
        
        Button btnReporteOtrosFormatos = new Button("Reporte Otros Formatos..");
        btnReporteOtrosFormatos.setOnAction((ActionEvent e)->{
         try{
            
           /* User home directory location */
            String userHomeDirectory = System.getProperty("user.home");
            /* Output file location */
            
            File file;
            JasperReport jasperReport;
            file = new File("Reportes/Formatos/reporteInventario.jasper");
            jasperReport = (JasperReport) JRLoader.loadObject(file);
            LocalDateTime ld = LocalDateTime.now();
            String fechaFile = String.valueOf(ld.getDayOfMonth())+String.valueOf(ld.getMonth())+String.valueOf(ld.getYear())+String.valueOf(ld.getHour())+String.valueOf(ld.getMinute())+String.valueOf(ld.getSecond());
            //String outputFile = userHomeDirectory + File.separatorChar + "ReporteInventario"+fechaFile+".pdf";
            //String outputFile = "Reportes/Inventario/" + File.separatorChar + "ReporteInventario"+fechaFile+".pdf";
            //JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tvProductos.getItems().subList(0, tvProductos.getItems().size()-1));
           JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tvProductos.getItems());
           System.out.println("Hay "+tvProductos.getItems().size());
            List<String> listWhere = new ArrayList<>();
            listWhere.add("id_suc = 1");
            List<SUCURSAL> sucList = sucDAO.consultaSucursal(listWhere);
            SUCURSAL sucIdent = sucList.get(0);
           Map<String, Object> parameters = new HashMap<>();
           parameters.put("ItemDataSource", itemsJRBean);
           parameters.put("Sucursal", sucIdent.getNombre());
           parameters.put("Fecha", LocalDate.now().toString());
           /* Generando el PDF */
            //C:\Users\dopcan\Documents\NetBeansProjects\ClasesConsultorio\src\gestionconsultorio
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            JRViewer jrViewer;
            JPanel jpanel;
            SwingNode swingNode;
            jpanel = new JPanel();
            swingNode = new SwingNode();
            jrViewer = new JRViewer(jasperPrint);
            jrViewer.setBounds(0, 0, 1100, 700);
            jpanel.setLayout(null);
            jpanel.add(jrViewer);
            jpanel.setSize(1100, 700);
            Pane panePreview = new Pane(); 
            panePreview.setPrefSize(1100, 700);
            panePreview.getChildren().add(swingNode);
            swingNode.setContent(jpanel);


            StackPane rootSelectClientes = new StackPane();
            rootSelectClientes.getChildren().addAll(swingNode);
       
            Scene scene = new Scene(rootSelectClientes,1100,700);
            Stage stgPpal = new Stage();
            stgPpal.setScene(scene);
            stgPpal.initModality(Modality.WINDOW_MODAL);
            stgPpal.show();  
        } catch (JRException ex) {
            ex.printStackTrace();
        } 

        });       
        HBox hbBotones = new HBox();
        hbBotones.setAlignment(Pos.CENTER_RIGHT);
        hbBotones.setSpacing(10);
        hbBotones.getChildren().addAll(btnCancelar, btnReportePdf, btnReporteOtrosFormatos);        

        GridPane gpPpal = new GridPane();
        gpPpal.setPadding(new Insets(5,5,5,5));
        gpPpal.setVgap(5);
        gpPpal.setHgap(5);
        
        gpPpal.add(lbCodigo_prod, 0, 0);
        gpPpal.add(tfCodigo_prod, 1, 0);
        gpPpal.add(lbDescripcion, 2, 0);
        gpPpal.add(tfDescripcion, 3, 0);
        gpPpal.add(lbCosto_compra, 0, 1);
        gpPpal.add(tfCosto_compra, 1, 1);
        gpPpal.add(lbCodigo_prov, 2, 1);
        gpPpal.add(tfCodigo_prov, 3, 1);
        gpPpal.add(lbUnidad_medida, 0, 2);
        gpPpal.add(tfUnidad_medida, 1, 2);
        gpPpal.add(lbExistencia, 2, 2);
        gpPpal.add(tfExistencia, 3, 2);       
        gpPpal.add(lbPrecio_menudeo, 0, 3);
        gpPpal.add(tfPrecio_menudeo, 1, 3);
        gpPpal.add(lbPrecio_mayoreo, 2, 3);
        gpPpal.add(tfPrecio_mayoreo, 3, 3);
        gpPpal.add(lbId_ubicacion, 0, 4);
        gpPpal.add(tfId_ubicacion, 1, 4);
        gpPpal.add(lbCategoriaMod, 2, 4);
        gpPpal.add(cbCategoriaMod, 3, 4);
        gpPpal.add(hbBotones, 3, 5);
        vbPpal.getChildren().addAll(lbTituloVista, lbTipoBusqueda, hbTipoSeleccion, hbCompSeleccion, tvProductos, gpPpal);
        return vbPpal;
    
    }
    private VBox vistaEliminarProducto(){
        VBox vbPpal= new VBox();
        Label lbTituloVista = new Label("ELIMINAR PRODUCTOS");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        Label lbTablaProducto = new Label("Productos");
        TableView tvProductos = new TableView();
        tvProductos.setMaxHeight(320);
        List<String> lstWhere = new ArrayList<>();
        lstWhere.add("codigo_prod is not null");
        List<String> lstCategorias = new ArrayList<>();
        lstCategorias.add("");
        List<String> lstWherecat = new ArrayList<>();
        lstWherecat.add("id_categoria is not null");
       for (categoria i : categDAO.consultarCategoria(lstWherecat)){
            lstCategorias.add(i.getCategoria());
       }
        //Componentes de Interfaz--------------------------------------------------------------
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgBusquedas = new ToggleGroup();
        
        RadioButton rbCodigo = new RadioButton("Codigo");
        RadioButton rbDescripcion = new RadioButton("Descripción");
        RadioButton rbCategoria = new RadioButton("Categoria");
        RadioButton rbTodos = new RadioButton("Todos");
        rbCodigo.setToggleGroup(tgBusquedas);
        rbDescripcion.setToggleGroup(tgBusquedas);
        rbCategoria.setToggleGroup(tgBusquedas);        
        rbTodos.setToggleGroup(tgBusquedas); 
        rbTodos.setSelected(true);
        
        Label lbCodigo = new Label("Codigo: ");
        TextField tfCodigo = new TextField();
        Label lbBusquedaDescripcion = new Label("Por Descripcion: ");
        TextField tfBusquedaDescripcion = new TextField();
        Label lbCategoria = new Label("Categoria: ");
        ComboBox cbCategoria = new ComboBox(FXCollections.observableList(lstCategorias));
        cbCategoria.setPrefWidth(140);        
        
        HBox hbCompSeleccion = new HBox();
        Button btnBuscarProductos = new Button("Seleccionar");
        btnBuscarProductos.setMaxHeight(50);
        btnBuscarProductos.setOnAction((ActionEvent e)->{
         
         if (rbTodos.isSelected()){
           lstWhere.add("codigo_prod is not null");
           tvProductos.setItems(invent.consultarInventario(lstWhere));
         }    
            
         if (rbCodigo.isSelected()){
           lstWhere.add("codigo_prod = "+tfCodigo.getText());
           tvProductos.setItems(invent.consultarInventario(lstWhere));
         }
         
         if (rbDescripcion.isSelected()){
           lstWhere.add("descripcion like '%"+tfBusquedaDescripcion.getText()+"%' ");
           tvProductos.setItems(invent.consultarInventario(lstWhere));
         }
         
         if (rbCategoria.isSelected()){
           String strCat = cbCategoria.getValue().toString();
           if (strCat.compareTo("")==0){
                  lstWhere.add("id_categoria = 0");
                  tvProductos.setItems(invent.consultarInventario(lstWhere));
           }else{
              lstWhere.add("id_categoria ='"+categDAO.consultaIdCategoria(strCat)+"' ");
              tvProductos.setItems(invent.consultarInventario(lstWhere));
           }
         }

           String titulo = "ELIMINAR PRODUCTOS ("+String.valueOf(tvProductos.getItems().size())+" Seleccionados)";
           lbTituloVista.setText(titulo);
        });
        
        VBox vbCodigo = new VBox();
        VBox vbDesc = new VBox();
        VBox vbCat = new VBox();
        vbCodigo.getChildren().addAll(lbCodigo, tfCodigo);
        vbDesc.getChildren().addAll(lbBusquedaDescripcion, tfBusquedaDescripcion);
        vbCat.getChildren().addAll(lbCategoria, cbCategoria);
        
        
        hbCompSeleccion.getChildren().addAll(vbCodigo, vbDesc, vbCat, btnBuscarProductos);
        hbCompSeleccion.setPadding(new Insets(10, 10, 10, 10));
        hbCompSeleccion.setSpacing(5);
        
        HBox hbTipoSeleccion = new HBox();
        hbTipoSeleccion.getChildren().addAll(rbTodos, rbCodigo, rbDescripcion, rbCategoria);
        hbTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        hbTipoSeleccion.setSpacing(5);
        
        
        TableColumn<inventario, Integer> claveProdColumna = new TableColumn<>("Codigo Producto");
        claveProdColumna.setMinWidth(120);
        claveProdColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));

        TableColumn<inventario, Integer> existenciaColumna = new TableColumn<>("Existencia");
        existenciaColumna.setMinWidth(120);
        existenciaColumna.setCellValueFactory(new PropertyValueFactory<>("existencia"));
        
        TableColumn<inventario, Integer> idUbicacionColumna = new TableColumn<>("Id Ubicación");
        idUbicacionColumna.setMinWidth(120);
        idUbicacionColumna.setCellValueFactory(new PropertyValueFactory<>("id_ubicacion"));
        
        TableColumn<inventario, Float> pMenudeoColumna = new TableColumn<>("Precio Menudeo");
        pMenudeoColumna.setMinWidth(120);
        pMenudeoColumna.setCellValueFactory(new PropertyValueFactory<>("precio_menudeo"));        

        TableColumn<inventario, Float> pMayoreoColumna = new TableColumn<>("Precio Mayoreo");
        pMayoreoColumna.setMinWidth(120);
        pMayoreoColumna.setCellValueFactory(new PropertyValueFactory<>("precio_mayoreo"));
        
        TableColumn<inventario, String> descripcionColumna = new TableColumn<>("Descripción");
        descripcionColumna.setMinWidth(120);
        descripcionColumna.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        
        TableColumn<inventario, String> uMedidaColumna = new TableColumn<>("Unidad Medidad");
        uMedidaColumna.setMinWidth(120);
        uMedidaColumna.setCellValueFactory(new PropertyValueFactory<>("unidad_medida"));

        TableColumn<inventario, Float> cCompraColumna = new TableColumn<>("Costo Compra");
        cCompraColumna.setMinWidth(120);
        cCompraColumna.setCellValueFactory(new PropertyValueFactory<>("costo_compra"));
        
        TableColumn<inventario, Integer> codProvColumna = new TableColumn<>("Codigo Proveedor");
        codProvColumna.setMinWidth(120);
        codProvColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prov"));
        
        TableColumn<inventario, Integer> idCategoriaColumna = new TableColumn<>("Id Categoria");
        idCategoriaColumna.setMinWidth(120);
        idCategoriaColumna.setCellValueFactory(new PropertyValueFactory<>("id_categoria"));
        
        tvProductos.getColumns().addAll(claveProdColumna, existenciaColumna, idUbicacionColumna,
                pMenudeoColumna, pMayoreoColumna, descripcionColumna, uMedidaColumna, 
                cCompraColumna, codProvColumna,idCategoriaColumna);
        
         List<inventario> lstInv = new ArrayList<>();
         lstWhere.add("codigo_prod is not null");
         lstInv=invent.consultaInventario(lstWhere);
         ObservableList obList = FXCollections.observableArrayList(lstInv);
         tvProductos.setItems(obList);
         String titulo = lbTituloVista.getText()+" ("+String.valueOf(obList.size())+")";
         lbTituloVista.setText(titulo);
        
        vbPpal.setAlignment(Pos.CENTER);
        Label lbCodigo_prod = new Label("Codigo Producto: ");
	Label lbExistencia = new Label("Existencias: ");
	Label lbId_ubicacion = new Label("Ubicación: ");
	Label lbPrecio_menudeo = new Label("Precio Menudeo: ");
	Label lbPrecio_mayoreo = new Label("Precio Mayoreo: ");
	Label lbDescripcion = new Label("Descripción: ");
	Label lbUnidad_medida = new Label("Unidad Medida: ");
	Label lbCosto_compra = new Label("Costo Compra");
	Label lbCodigo_prov = new Label("Código Proveedor: ");

        TextField tfCodigo_prod = new TextField();
        tfCodigo_prod.setMaxWidth(80);
        tfCodigo_prod.setEditable(false);
	TextField tfExistencia = new TextField();
        tfExistencia.setMaxWidth(80);
        tfExistencia.setEditable(false);
	TextField tfId_ubicacion = new TextField();
        tfId_ubicacion.setMaxWidth(80);
        tfId_ubicacion.setEditable(false);
	TextField tfPrecio_menudeo = new TextField();
        tfPrecio_menudeo.setMaxWidth(120);
        tfPrecio_menudeo.setEditable(false);
	TextField tfPrecio_mayoreo = new TextField();
        tfPrecio_mayoreo.setMaxWidth(120);
        tfPrecio_mayoreo.setEditable(false);
	TextArea tfDescripcion = new TextArea();
        tfDescripcion.setMaxHeight(40);
        tfDescripcion.setMaxWidth(280);
        tfDescripcion.setEditable(false);
	ComboBox tfUnidad_medida = new ComboBox();
        ObservableList<String> options = FXCollections.observableArrayList( 
           "PZA.",
           "Mts.",
           "Unidad",
           "Kg",
           "grm",
           "PQT."
        );
        tfUnidad_medida.setEditable(false);
        tfUnidad_medida.setMaxWidth(120);
	TextField tfCosto_compra = new TextField();
        tfCosto_compra.setMaxWidth(120);
        tfCosto_compra.setEditable(false);
	TextField tfCodigo_prov = new TextField();
        tfCodigo_prov.setMaxWidth(120);
        tfCodigo_prov.setEditable(false);
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                vbAreaTrabajo.getChildren().remove(0);
            }
        });

        tvProductos.setOnMouseClicked((event) -> {
            inventario inv= (inventario) tvProductos.getSelectionModel().getSelectedItem();
            tfDescripcion.setText(inv.getDescripcion());
            tfCodigo_prod.setText(String.valueOf(inv.getCodigo_prod()));
            tfCosto_compra.setText(String.valueOf(inv.getCosto_compra()));
            tfCodigo_prov.setText(String.valueOf(inv.getCodigo_prov()));
            tfExistencia.setText(String.valueOf(inv.getExistencia()));
            tfPrecio_menudeo.setText(String.valueOf(inv.getPrecio_menudeo()));
            tfPrecio_mayoreo.setText(String.valueOf(inv.getPrecio_mayoreo()));
            tfId_ubicacion.setText(String.valueOf(inv.getId_ubicacion()));
            tfUnidad_medida.setValue(inv.getUnidad_medida());
        });
        
        Button btnEliminar = new Button("Eliminar");
        btnEliminar.setOnAction((ActionEvent e)->{
           inventarioDAO invDAO = new inventarioDAO();
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setHeaderText(null);
           alert.setTitle("Confirmación");
           alert.setContentText("¿Estas seguro de confirmar la acción?");
           Optional<ButtonType> action = alert.showAndWait(); 
           if (action.get() == ButtonType.OK) {
            invDAO.EliminarLogicamenteProducto(Integer.parseInt(tfCodigo_prod.getText()));
            removerVistas();
           } else {
            removerVistas();
           }
        });
        
        
        HBox hbBotones = new HBox();
        hbBotones.setAlignment(Pos.CENTER_RIGHT);
        hbBotones.getChildren().addAll(btnCancelar, btnEliminar);        

        GridPane gpPpal = new GridPane();
        gpPpal.setPadding(new Insets(5,5,5,5));
        gpPpal.setVgap(5);
        gpPpal.setHgap(5);
        
        gpPpal.add(lbCodigo_prod, 0, 0);
        gpPpal.add(tfCodigo_prod, 1, 0);
        gpPpal.add(lbDescripcion, 2, 0);
        gpPpal.add(tfDescripcion, 3, 0);
        gpPpal.add(lbCosto_compra, 0, 1);
        gpPpal.add(tfCosto_compra, 1, 1);
        gpPpal.add(lbCodigo_prov, 2, 1);
        gpPpal.add(tfCodigo_prov, 3, 1);
        gpPpal.add(lbUnidad_medida, 0, 2);
        gpPpal.add(tfUnidad_medida, 1, 2);
        gpPpal.add(lbExistencia, 2, 2);
        gpPpal.add(tfExistencia, 3, 2);       
        gpPpal.add(lbPrecio_menudeo, 0, 3);
        gpPpal.add(tfPrecio_menudeo, 1, 3);
        gpPpal.add(lbPrecio_mayoreo, 2, 3);
        gpPpal.add(tfPrecio_mayoreo, 3, 3);
        gpPpal.add(lbId_ubicacion, 0, 4);
        gpPpal.add(tfId_ubicacion, 1, 4);
        gpPpal.add(hbBotones, 3, 4);
        gpPpal.setAlignment(Pos.CENTER);
        vbPpal.getChildren().addAll(lbTituloVista, hbTipoSeleccion, hbCompSeleccion, tvProductos, gpPpal);
        return vbPpal;
    
    }
    private VBox vistaNuevoCliente(){
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("AGREGAR CLIENTE");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        //Etiquetas/Datos de Cliente;
	Label lbNombre  = new Label("Nombre: ");
	Label lbRazon_social  = new Label("Razon Social");
	Label lbDomicilio_fiscal  = new Label("Dom. Fiscal");
	Label lbTelefono  = new Label("Telefono.");
	Label lbRFC  = new Label("RFC");
	Label lbEmail  = new Label("Email:");
        
        TextField tfNombre = new TextField();
        tfNombre.setPrefWidth(320);
        TextField tfRazonSocial = new TextField();
        tfRazonSocial.setPrefWidth(320);
        TextField tfDomicilioFiscal = new TextField();
        tfDomicilioFiscal.setMaxWidth(320);
        TextField tfTelefono = new TextField();
        tfTelefono.setMaxWidth(200);
        TextField tfRFC = new TextField();
        tfRFC.setMaxWidth(120);
        TextField tfEmail = new TextField();
        tfEmail.setMaxWidth(320);
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction((ActionEvent e)->{
            removerVistas();
        });
        
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction((ActionEvent e)->{
           clienteDAO cliDAO = new clienteDAO();
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setHeaderText(null);
           alert.setTitle("Confirmación");
           alert.setContentText("¿Estas seguro de confirmar la acción?");
           Optional<ButtonType> action = alert.showAndWait(); 
           if (action.get() == ButtonType.OK) {
              cliDAO.insertarCliente(tfNombre.getText(), tfRazonSocial.getText(), tfDomicilioFiscal.getText(), 
                      tfTelefono.getText(), tfRFC.getText(), tfEmail.getText());
              removerVistas();
           } else {
              removerVistas();
           }

        });
        
        HBox hbBotones = new HBox();
        hbBotones.setSpacing(5);
        hbBotones.getChildren().addAll(btnCancelar, btnGuardar);
        
        GridPane gpClienteSeleccionado = new GridPane();
        gpClienteSeleccionado.setPadding(new Insets(5, 5, 5, 5));
        gpClienteSeleccionado.setVgap(10);
        gpClienteSeleccionado.setHgap(10);
        
        gpClienteSeleccionado.add(lbNombre, 0, 0);
        gpClienteSeleccionado.add(tfNombre, 1, 0);
        gpClienteSeleccionado.add(lbRazon_social, 2, 0);
        gpClienteSeleccionado.add(tfRazonSocial, 3, 0);
        
        gpClienteSeleccionado.add(lbDomicilio_fiscal, 0, 1);
        gpClienteSeleccionado.add(tfDomicilioFiscal, 1, 1);
        gpClienteSeleccionado.add(lbTelefono, 2, 1);
        gpClienteSeleccionado.add(tfTelefono, 3, 1);
        
        gpClienteSeleccionado.add(lbRFC, 0, 2);
        gpClienteSeleccionado.add(tfRFC, 1, 2);
        gpClienteSeleccionado.add(lbEmail, 2, 2);
        gpClienteSeleccionado.add(tfEmail, 3, 2);
        
        gpClienteSeleccionado.add(hbBotones, 3, 3);
        
        vbPpal.getChildren().addAll(lbTituloVista, gpClienteSeleccionado);
        
        return vbPpal;
    }
    private VBox vistaModificarCliente(){
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("MODIFICAR CLIENTES");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);

        
        TableView tvClientes = new TableView();
        
        TableColumn<CLIENTE, String> codClientColumna = new TableColumn<>("Codigo Cliente");
        codClientColumna.setMinWidth(120);
        codClientColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_cliente"));
        
        TableColumn<CLIENTE, String> nombreColumna = new TableColumn<>("Nombre");
        nombreColumna.setMinWidth(320);
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        
        TableColumn<CLIENTE, String> razonSocialColumna = new TableColumn<>("Razon Social");
        razonSocialColumna.setMinWidth(320);
        razonSocialColumna.setCellValueFactory(new PropertyValueFactory<>("razon_social"));
        
        TableColumn<CLIENTE, String> domFiscalColumna = new TableColumn<>("Domicilio Fiscal");
        domFiscalColumna.setMinWidth(320);
        domFiscalColumna.setCellValueFactory(new PropertyValueFactory<>("domicilio_fiscal"));
        
        TableColumn<CLIENTE, String> telefonoColumna = new TableColumn<>("Telefono");
        telefonoColumna.setMinWidth(120);
        telefonoColumna.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        
        TableColumn<CLIENTE, String> rfcColumna = new TableColumn<>("RFC");
        rfcColumna.setMinWidth(120);
        rfcColumna.setCellValueFactory(new PropertyValueFactory<>("rfc"));
        
        TableColumn<CLIENTE, String> emailColumna = new TableColumn<>("E-Mail");
        emailColumna.setMinWidth(120);
        emailColumna.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        tvClientes.getColumns().addAll(codClientColumna, nombreColumna, razonSocialColumna, domFiscalColumna,
                telefonoColumna, rfcColumna, emailColumna);
        
         List<CLIENTE> lstInv = new ArrayList<>();
         List<String> lstWhere = new ArrayList<>();
         lstWhere.add("codigo_cliente is not null");
         lstInv=cliDAO.consultarClientes(lstWhere);
         ObservableList obList = FXCollections.observableArrayList(lstInv);
         tvClientes.setItems(obList);
         String titulo = lbTituloVista.getText()+" ("+String.valueOf(obList.size())+")";
         lbTituloVista.setText(titulo);

        //Etiquetas/Datos de Cliente;
	Label lbNombre  = new Label("Nombre: ");
	Label lbRazon_social  = new Label("Razon Social");
	Label lbDomicilio_fiscal  = new Label("Dom. Fiscal");
	Label lbTelefono  = new Label("Telefono.");
	Label lbRFC  = new Label("RFC");
	Label lbEmail  = new Label("Email:");
        
        TextField tfNombre = new TextField();
        tfNombre.setPrefWidth(320);
        TextField tfRazonSocial = new TextField();
        tfRazonSocial.setPrefWidth(320);
        TextField tfDomicilioFiscal = new TextField();
        tfDomicilioFiscal.setMaxWidth(320);
        TextField tfTelefono = new TextField();
        tfTelefono.setMaxWidth(200);
        TextField tfRFC = new TextField();
        tfRFC.setMaxWidth(120);
        TextField tfEmail = new TextField();
        tfEmail.setMaxWidth(320);
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction((ActionEvent e)->{
            vbAreaTrabajo.getChildren().remove(0);
        });
        
        tvClientes.setOnMouseClicked((event) -> {
            CLIENTE cli= (CLIENTE) tvClientes.getSelectionModel().getSelectedItem();
            tfNombre.setText(cli.getNombre());
            tfDomicilioFiscal.setText(cli.getDomicilio_fiscal());
            tfRazonSocial.setText(cli.getRazon_social());
            tfRFC.setText(cli.getRfc());
            tfTelefono.setText(cli.getTelefono());
            tfEmail.setText(cli.getEmail());
        });
        
        Button btnGuardar = new Button("Modificar");
        btnGuardar.setOnAction((ActionEvent e)->{
           CLIENTE cli= (CLIENTE) tvClientes.getSelectionModel().getSelectedItem();
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setHeaderText(null);
           alert.setTitle("Confirmación");
           alert.setContentText("¿Estas seguro de confirmar la acción?");
           Optional<ButtonType> action = alert.showAndWait(); 
           if (action.get() == ButtonType.OK) {
              cliDAO.modificarCliente(cli.getCodigo_cliente(), 
                      tfNombre.getText(), tfRazonSocial.getText(), tfDomicilioFiscal.getText(), 
                      tfTelefono.getText(), tfRFC.getText(), tfEmail.getText());
              removerVistas();
           } else {
              removerVistas();
           }
        });
        
        HBox hbBotones = new HBox();
        hbBotones.setSpacing(5);
        hbBotones.getChildren().addAll(btnCancelar, btnGuardar);
        
        GridPane gpClienteSeleccionado = new GridPane();
        gpClienteSeleccionado.setPadding(new Insets(5, 5, 5, 5));
        gpClienteSeleccionado.setVgap(10);
        gpClienteSeleccionado.setHgap(10);
        
        gpClienteSeleccionado.add(lbNombre, 0, 0);
        gpClienteSeleccionado.add(tfNombre, 1, 0);
        gpClienteSeleccionado.add(lbRazon_social, 2, 0);
        gpClienteSeleccionado.add(tfRazonSocial, 3, 0);
        
        gpClienteSeleccionado.add(lbDomicilio_fiscal, 0, 1);
        gpClienteSeleccionado.add(tfDomicilioFiscal, 1, 1);
        gpClienteSeleccionado.add(lbTelefono, 2, 1);
        gpClienteSeleccionado.add(tfTelefono, 3, 1);
        
        gpClienteSeleccionado.add(lbRFC, 0, 2);
        gpClienteSeleccionado.add(tfRFC, 1, 2);
        gpClienteSeleccionado.add(lbEmail, 2, 2);
        gpClienteSeleccionado.add(tfEmail, 3, 2);
        
        gpClienteSeleccionado.add(hbBotones, 3, 3);
        
        vbPpal.getChildren().addAll(lbTituloVista, tvClientes, gpClienteSeleccionado);
        
        return vbPpal;
    }
    private VBox vistaEliminarCliente(){
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("ELIMINAR CLIENTES");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);

        TableView tvClientes = new TableView();        

        TableColumn<CLIENTE, String> codClientColumna = new TableColumn<>("Codigo Cliente");
        codClientColumna.setMinWidth(120);
        codClientColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_cliente"));
        
        TableColumn<CLIENTE, String> nombreColumna = new TableColumn<>("Nombre");
        nombreColumna.setMinWidth(320);
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        
        TableColumn<CLIENTE, String> razonSocialColumna = new TableColumn<>("Razon Social");
        razonSocialColumna.setMinWidth(320);
        razonSocialColumna.setCellValueFactory(new PropertyValueFactory<>("razon_social"));
        
        TableColumn<CLIENTE, String> domFiscalColumna = new TableColumn<>("Domicilio Fiscal");
        domFiscalColumna.setMinWidth(320);
        domFiscalColumna.setCellValueFactory(new PropertyValueFactory<>("domicilio_fiscal"));
        
        TableColumn<CLIENTE, String> telefonoColumna = new TableColumn<>("Telefono");
        telefonoColumna.setMinWidth(120);
        telefonoColumna.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        
        TableColumn<CLIENTE, String> rfcColumna = new TableColumn<>("RFC");
        rfcColumna.setMinWidth(120);
        rfcColumna.setCellValueFactory(new PropertyValueFactory<>("rfc"));
        
        TableColumn<CLIENTE, String> emailColumna = new TableColumn<>("E-Mail");
        emailColumna.setMinWidth(120);
        emailColumna.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        tvClientes.getColumns().addAll(codClientColumna, nombreColumna, razonSocialColumna, domFiscalColumna,
                telefonoColumna, rfcColumna, emailColumna);
        
         List<CLIENTE> lstInv = new ArrayList<>();
         List<String> lstWhere = new ArrayList<>();
         lstWhere.add("codigo_cliente is not null");
         lstInv=cliDAO.consultarClientes(lstWhere);
         ObservableList obList = FXCollections.observableArrayList(lstInv);
         tvClientes.setItems(obList);
         String titulo = lbTituloVista.getText()+" ("+String.valueOf(obList.size())+")";
         lbTituloVista.setText(titulo);


        //Etiquetas/Datos de Cliente;
        Label lbCodigo_cliente  = new Label("Codigo_Cliente");
	Label lbNombre  = new Label("Nombre: ");
	Label lbRazon_social  = new Label("Razon Social");
	Label lbDomicilio_fiscal  = new Label("Dom. Fiscal");
	Label lbTelefono  = new Label("Telefono.");
	Label lbRFC  = new Label("RFC");
	Label lbEmail  = new Label("Email:");
        
        TextField tfCodigoCliente = new TextField();
        tfCodigoCliente.setMaxWidth(120);
        tfCodigoCliente.setEditable(false);
        TextField tfNombre = new TextField();
        tfNombre.setPrefWidth(320);
        tfNombre.setEditable(false);
        TextField tfRazonSocial = new TextField();
        tfRazonSocial.setPrefWidth(320);
        tfRazonSocial.setEditable(false);
        TextField tfDomicilioFiscal = new TextField();
        tfDomicilioFiscal.setMaxWidth(320);
        tfDomicilioFiscal.setEditable(false);
        TextField tfTelefono = new TextField();
        tfTelefono.setMaxWidth(200);
        tfTelefono.setEditable(false);
        TextField tfRFC = new TextField();
        tfRFC.setMaxWidth(120);
        tfRFC.setEditable(false);
        TextField tfEmail = new TextField();
        tfEmail.setMaxWidth(320);
        tfEmail.setEditable(false);
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction((ActionEvent e)->{
            removerVistas();
        });
        
        tvClientes.setOnMouseClicked((event) -> {
            CLIENTE cli= (CLIENTE) tvClientes.getSelectionModel().getSelectedItem();
            tfCodigoCliente.setText(String.valueOf(cli.getCodigo_cliente()));
            tfNombre.setText(cli.getNombre());
            tfDomicilioFiscal.setText(cli.getDomicilio_fiscal());
            tfRazonSocial.setText(cli.getRazon_social());
            tfRFC.setText(cli.getRfc());
            tfTelefono.setText(cli.getTelefono());
            tfEmail.setText(cli.getEmail());
        });

        
        Button btnEliminar = new Button("Eliminar");
        btnEliminar.setOnAction((ActionEvent e)->{
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setHeaderText(null);
           alert.setTitle("Confirmación");
           alert.setContentText("¿Estas seguro de confirmar la acción?");
           Optional<ButtonType> action = alert.showAndWait(); 
           if (action.get() == ButtonType.OK) {
              cliDAO.eliminarCliente(Integer.parseInt(tfCodigoCliente.getText()));
              removerVistas();
           } else {
              removerVistas();
           }

        });
        
        HBox hbBotones = new HBox();
        hbBotones.setSpacing(5);
        hbBotones.getChildren().addAll(btnCancelar, btnEliminar);
        
        GridPane gpClienteSeleccionado = new GridPane();
        gpClienteSeleccionado.setPadding(new Insets(5, 5, 5, 5));
        gpClienteSeleccionado.setVgap(10);
        gpClienteSeleccionado.setHgap(10);
        
        gpClienteSeleccionado.add(lbCodigo_cliente, 0, 0);
        gpClienteSeleccionado.add(tfCodigoCliente, 1, 0);
        gpClienteSeleccionado.add(lbNombre, 2, 0);
        gpClienteSeleccionado.add(tfNombre, 3, 0);
        
        gpClienteSeleccionado.add(lbRazon_social, 0, 1);
        gpClienteSeleccionado.add(tfRazonSocial, 1, 1);
        gpClienteSeleccionado.add(lbDomicilio_fiscal, 2, 1);
        gpClienteSeleccionado.add(tfDomicilioFiscal, 3, 1);
        
        gpClienteSeleccionado.add(lbTelefono, 0, 2);
        gpClienteSeleccionado.add(tfTelefono, 1, 2);
        gpClienteSeleccionado.add(lbRFC, 2, 2);
        gpClienteSeleccionado.add(tfRFC, 3, 2);
        
        gpClienteSeleccionado.add(lbEmail, 0, 3);
        gpClienteSeleccionado.add(tfEmail, 1, 3);
        gpClienteSeleccionado.add(hbBotones, 3, 3);
        
        vbPpal.getChildren().addAll(lbTituloVista, tvClientes, gpClienteSeleccionado);
        
        return vbPpal;
    }
    private VBox vistaGestionCreditosCliente(){
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER_LEFT);
        Label lbTituloVista = new Label("GESTION PAGOS A CREDITOS DE CLIENTES");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        Label lbEtiquetaTotalCredito = new Label("Total Credito:   ");
        lbEtiquetaTotalCredito.setFont(fuente);
        Label lbTotalCredito = new Label("$ 0.0");
        lbTotalCredito.setFont(fuente);
        lbTotalCredito.setAlignment(Pos.CENTER_RIGHT);
        Label lbEtiquetaTotalCubierto = new Label("Total Cubierto: ");
        lbEtiquetaTotalCubierto.setFont(fuente);
        Label lbTotalCubierto = new Label("$ 0.0");
        lbTotalCubierto.setFont(fuente);
        lbTotalCubierto.setAlignment(Pos.CENTER_RIGHT);
        Label lbEtiquetaResto = new Label("Resta :");
        lbEtiquetaResto.setFont(fuente);
        Label lbResto = new Label("$ 0.0");
        lbResto.setFont(fuente);
        lbResto.setAlignment(Pos.CENTER_RIGHT);
        
        Label lbTablaClientes = new Label("Tabla Clientes: ");
        Label lbTablaCreditos = new Label("Tabla Creditos: ");
        Label lbTablaPagos = new Label("Tabla Pagos: ");
        Label lbidCredito = new Label("Id Credito: ");
        Label lbfolio = new Label("Folio: ");
        Label lbFechaPago = new Label("Fecha Pago: ");
        Label lbMonto = new Label("Monto del Pago: ");
        
        TextField tfClientes = new TextField();
        TextField tfidCredito = new TextField();
        TextField tffolio = new TextField();
        DatePicker dpFechaPago = new DatePicker(LocalDate.now());
        TextField tfMonto = new TextField();
        
        Button btnCancelar = new Button("Cancelar");
        Button btnRegPago = new Button("Reg. Pago");

        GridPane gpDatosPago = new GridPane();
        gpDatosPago.setVgap(10);
        gpDatosPago.setHgap(10);
        
        gpDatosPago.add(lbidCredito, 0, 0);
        gpDatosPago.add(tfidCredito, 1, 0);
        
        gpDatosPago.add(lbfolio, 2, 0);
        gpDatosPago.add(tffolio, 3, 0);
        
        gpDatosPago.add(lbFechaPago, 0, 1);
        gpDatosPago.add(dpFechaPago, 1, 1);
        
        gpDatosPago.add(lbMonto, 2, 1);
        gpDatosPago.add(tfMonto, 3, 1);

        gpDatosPago.add(btnCancelar, 2, 2);
        gpDatosPago.add(btnRegPago, 3, 2);
        
        gpDatosPago.add(lbEtiquetaTotalCredito, 1, 3);
        gpDatosPago.add(lbTotalCredito, 2, 3);
        gpDatosPago.add(lbEtiquetaTotalCubierto, 1, 4);
        gpDatosPago.add(lbTotalCubierto, 2, 4);
        gpDatosPago.add(lbEtiquetaResto, 1, 5);
        gpDatosPago.add(lbResto, 2, 5);
        
        TableView tvClientes = new TableView(); 
        
        TableColumn<CLIENTE, String> codClienteColumna = new TableColumn<>("Codigo Cliente");
        codClienteColumna.setMinWidth(120);
        codClienteColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_cliente"));
        
        TableColumn<CLIENTE, String> nombreColumna = new TableColumn<>("Nombre");
        nombreColumna.setMinWidth(320);
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        tvClientes.getColumns().addAll(codClienteColumna, nombreColumna);
        
         List<CLIENTE> lstClte = new ArrayList<>();
         List<String> lstWhere = new ArrayList<>();
         lstWhere.add("codigo_cliente is not null");
         lstClte=cliDAO.consultarClientes(lstWhere);
         ObservableList obList = FXCollections.observableArrayList(lstClte);
         tvClientes.setItems(obList);

         TableView tvCreditos = new TableView();
         
        TableColumn<Credito, String> idCreditoColumna = new TableColumn<>("Id Credito");
        idCreditoColumna.setMinWidth(120);
        idCreditoColumna.setCellValueFactory(new PropertyValueFactory<>("id_credito"));         
  
        TableColumn<Credito, String> codigoClienteCreditoColumna = new TableColumn<>("Codigo Cliente");
        codigoClienteCreditoColumna.setMinWidth(120);
        codigoClienteCreditoColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_cliente")); 
        
        TableColumn<Credito, String> codigoNotaVentaCreditoColumna = new TableColumn<>("Codigo Nota de Venta");
        codigoNotaVentaCreditoColumna.setMinWidth(120);
        codigoNotaVentaCreditoColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_nota_venta")); 
        
        TableColumn<Credito, String> fechaCreditoColumna = new TableColumn<>("Fecha");
        fechaCreditoColumna.setMinWidth(120);
        fechaCreditoColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        
        TableColumn<Credito, String> montoCreditoColumna = new TableColumn<>("Monto Credito");
        montoCreditoColumna.setMinWidth(120);
        montoCreditoColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));
        
        tvCreditos.getColumns().addAll(idCreditoColumna, codigoClienteCreditoColumna, codigoNotaVentaCreditoColumna, 
                fechaCreditoColumna, montoCreditoColumna);
        
        
        
        List<String> lstWhereCre = new ArrayList<>();
        
        tvClientes.setOnMouseClicked((event) -> {
            lstPagosCre.remove(0, lstPagosCre.size());
            tfClientes.setText("");
            tfidCredito.setText("");
            tffolio.setText("");
            tfMonto.setText("");
            CLIENTE clTemp = (CLIENTE) tvClientes.getSelectionModel().getSelectedItem();
            lstWhereCre.add("codigo_cliente = "+clTemp.getCodigo_cliente());
            ObservableList<Credito> obsLstCreditos = FXCollections.observableArrayList();
            obsLstCreditos.addAll(creDao.consultaCredito(lstWhereCre));
            tvCreditos.setItems(obsLstCreditos);
            lbTotalCredito.setText("$ 0.0");
            lbTotalCubierto.setText("$ 0.0");
            lbResto.setText("$ 0.0");
        });
       vbPpal.setSpacing(5);
       
       btnCancelar.setOnAction((ActionEvent e)->{
           removerVistas();
       });
      
       TableView tvPagos = new TableView();
       
        TableColumn<pagos_credito, String> folioPagosCreditoColumna = new TableColumn<>("Folio Remision");
        folioPagosCreditoColumna.setMinWidth(120);
        folioPagosCreditoColumna.setCellValueFactory(new PropertyValueFactory<>("folio"));
       
        TableColumn<pagos_credito, String> fechaPagosCreditoColumna = new TableColumn<>("Fecha Pago");
        fechaPagosCreditoColumna.setMinWidth(120);
        fechaPagosCreditoColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        
        TableColumn<pagos_credito, String> idPagosCreditoColumna = new TableColumn<>("id Credito");
        idPagosCreditoColumna.setMinWidth(120);
        idPagosCreditoColumna.setCellValueFactory(new PropertyValueFactory<>("id_credito"));   
        
        TableColumn<pagos_credito, Float> montoPagosCreditoColumna = new TableColumn<>("Monto Pago");
        montoPagosCreditoColumna.setMinWidth(120);
        montoPagosCreditoColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));
        
        tvPagos.getColumns().addAll(folioPagosCreditoColumna, idPagosCreditoColumna, fechaPagosCreditoColumna, 
                montoPagosCreditoColumna);
        tvPagos.setItems(lstPagosCre);

        
        MenuItem miEliminarPagos = new MenuItem("Eliminar Pago");
        miEliminarPagos.setOnAction((event) -> {
            pagos_credito pagCreTemp = (pagos_credito)tvPagos.getSelectionModel().getSelectedItem();
            pagcreDAO.borrarPagoCred(pagCreTemp.getId_pago_cre());
            lstPagosCre.clear();
            lstWhere.clear();
            lstWhere.add("id_credito = "+pagCreTemp.getId_credito());
            lstPagosCre = FXCollections.observableArrayList(pagcreDAO.consultaPagoCred(lstWhere));
            tvPagos.setItems(lstPagosCre);
            Credito creIdent = (Credito) tvCreditos.getSelectionModel().getSelectedItem();
            float sumTotCred = creIdent.getMonto();
            lbTotalCredito.setText(" $ "+String.valueOf(sumTotCred));
            float sumPagado=0;
            for (pagos_credito pg:lstPagosCre){
              sumPagado = sumPagado + pg.getMonto();
            }
            if (sumPagado > 0) lbTotalCubierto.setText(" $ "+String.valueOf(sumPagado));
            float resta = sumTotCred - sumPagado;
            lbResto.setText(" $ "+String.valueOf(resta));
            
        });
        ContextMenu cmMenuPagos = new ContextMenu();
        cmMenuPagos.getItems().addAll(miEliminarPagos);
        tvPagos.setContextMenu(cmMenuPagos);
        tvCreditos.setOnMouseClicked((event) -> {
            Credito creIdent = (Credito) tvCreditos.getSelectionModel().getSelectedItem();
            tfidCredito.setText(String.valueOf(creIdent.getId_credito()));
            float sumTotCred = creIdent.getMonto();
            lbTotalCredito.setText(" $ "+String.valueOf(sumTotCred));
            lstWhere.add("id_credito = "+creIdent.getId_credito());
            lstPagosCre = FXCollections.observableArrayList(pagcreDAO.consultaPagoCred(lstWhere));
            float sumPagado=0;
            for (pagos_credito pg:lstPagosCre){
              sumPagado = sumPagado + pg.getMonto();
            }
            if (sumPagado > 0){
                lbTotalCubierto.setText(" $ "+String.valueOf(sumPagado));
            } else lbTotalCubierto.setText(" $ 0.0");
            float resta = sumTotCred - sumPagado;
            lbResto.setText(" $ "+String.valueOf(resta));
            tvPagos.setItems(lstPagosCre);
        });        
        
        MenuItem miVerproductos = new MenuItem("Ver Productos..");
        miVerproductos.setOnAction((event) -> {
            Credito creTemp = (Credito) tvCreditos.getSelectionModel().getSelectedItem();
            ventanaVerProductosComprados(creTemp.getCodigo_nota_venta());
        }); 
        
        ContextMenu cmMenuCreditos = new ContextMenu(miVerproductos);
        tvCreditos.setContextMenu(cmMenuCreditos);
        btnRegPago.setOnAction((event) -> {
            
            Credito creIdent = (Credito) tvCreditos.getSelectionModel().getSelectedItem();
            float sumTotCred = creIdent.getMonto();
            lbTotalCredito.setText(" $ "+String.valueOf(sumTotCred));
            pagos_credito pagcre = new pagos_credito();
            pagcre.setId_credito(Integer.parseInt(tfidCredito.getText()));
            pagcre.setFolio(tffolio.getText());
            pagcre.setFecha(dpFechaPago.getValue().toString());
            pagcre.setMonto(Float.parseFloat(tfMonto.getText()));
            lstPagosCre.add(pagcre);
            pagcreDAO.insertarPagoCred(pagcre);
            float sumPagado=0;
            for (pagos_credito pg:lstPagosCre){
              sumPagado = sumPagado + pg.getMonto();
            }
            if (sumPagado > 0) lbTotalCubierto.setText(" $ "+String.valueOf(sumPagado));
            float resta = sumTotCred - sumPagado;
            lbResto.setText(" $ "+String.valueOf(resta));
            tfMonto.setText("");
            tffolio.setText("");
            //tvPagos.setItems(lstPagosCre);
            Alert altMensaje =new Alert(Alert.AlertType.INFORMATION);
            altMensaje.setContentText("Pago a credito Registrado");
            altMensaje.setTitle("Informacion-Credito");
            altMensaje.show();           
        });
        
       VBox vbIzq = new VBox();
       vbIzq.setSpacing(10);
       vbIzq.setAlignment(Pos.CENTER_LEFT);
       
       VBox vbDer = new VBox();
       vbDer.setSpacing(10);
       vbDer.setAlignment(Pos.TOP_LEFT);
       
       vbIzq.getChildren().addAll(lbTablaClientes, tvClientes, lbTablaCreditos, tvCreditos);
       vbDer.getChildren().addAll(lbTablaPagos, tvPagos, gpDatosPago);
       HBox hbCenter = new HBox(vbIzq, vbDer);
       hbCenter.setSpacing(10);
       vbPpal.getChildren().addAll(lbTituloVista, hbCenter);       
        
        return vbPpal;
    }   
    private VBox vistaGestionApartadosCliente(){
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER_LEFT);
        Label lbTituloVista = new Label("GESTION PAGOS A APARTADOS DE CLIENTES");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        Label lbEtiquetaTotalApartado = new Label("Total Apartado:   ");
        lbEtiquetaTotalApartado.setFont(fuente);
        Label lbTotalApartado = new Label("$ 0.0");
        lbTotalApartado.setFont(fuente);
        lbTotalApartado.setAlignment(Pos.CENTER_RIGHT);
        Label lbEtiquetaTotalCubierto = new Label("Total Cubierto: ");
        lbEtiquetaTotalCubierto.setFont(fuente);
        Label lbTotalCubierto = new Label("$ 0.0");
        lbTotalCubierto.setFont(fuente);
        lbTotalCubierto.setAlignment(Pos.CENTER_RIGHT);
        Label lbEtiquetaResto = new Label("Resta :");
        lbEtiquetaResto.setFont(fuente);
        Label lbResto = new Label("$ 0.0");
        lbResto.setFont(fuente);
        lbResto.setAlignment(Pos.CENTER_RIGHT);
        
        Label lbTablaClientes = new Label("Tabla Clientes: ");
        Label lbTablaApartados = new Label("Tabla Apartados: ");
        Label lbTablaPagos = new Label("Tabla Pagos: ");
        Label lbidApartado = new Label("Id Apartado: ");
        Label lbfolio = new Label("Folio: ");
        Label lbFechaPago = new Label("Fecha Pago: ");
        Label lbMonto = new Label("Monto del Pago: ");
        
        TextField tfClientes = new TextField();
        TextField tfidApartado = new TextField();
        TextField tffolio = new TextField();
        DatePicker dpFechaPago = new DatePicker(LocalDate.now());
        TextField tfMonto = new TextField();
        
        Button btnCancelar = new Button("Cancelar");
        Button btnRegPago = new Button("Reg. Pago");

        GridPane gpDatosPago = new GridPane();
        gpDatosPago.setVgap(10);
        gpDatosPago.setHgap(10);
        
        gpDatosPago.add(lbidApartado, 0, 0);
        gpDatosPago.add(tfidApartado, 1, 0);
        
        gpDatosPago.add(lbfolio, 2, 0);
        gpDatosPago.add(tffolio, 3, 0);
        
        gpDatosPago.add(lbFechaPago, 0, 1);
        gpDatosPago.add(dpFechaPago, 1, 1);
        
        gpDatosPago.add(lbMonto, 2, 1);
        gpDatosPago.add(tfMonto, 3, 1);

        gpDatosPago.add(btnCancelar, 2, 2);
        gpDatosPago.add(btnRegPago, 3, 2);
        
        gpDatosPago.add(lbEtiquetaTotalApartado, 1, 3);
        gpDatosPago.add(lbTotalApartado, 2, 3);
        gpDatosPago.add(lbEtiquetaTotalCubierto, 1, 4);
        gpDatosPago.add(lbTotalCubierto, 2, 4);
        gpDatosPago.add(lbEtiquetaResto, 1, 5);
        gpDatosPago.add(lbResto, 2, 5);
        
        TableView tvClientes = new TableView(); 
        
        TableColumn<CLIENTE, String> codClienteColumna = new TableColumn<>("Codigo Cliente");
        codClienteColumna.setMinWidth(120);
        codClienteColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_cliente"));
        
        TableColumn<CLIENTE, String> nombreColumna = new TableColumn<>("Nombre");
        nombreColumna.setMinWidth(320);
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        tvClientes.getColumns().addAll(codClienteColumna, nombreColumna);
        
         List<CLIENTE> lstClte = new ArrayList<>();
         List<String> lstWhere = new ArrayList<>();
         lstWhere.add("codigo_cliente is not null");
         lstClte=cliDAO.consultarClientes(lstWhere);
         ObservableList obList = FXCollections.observableArrayList(lstClte);
         tvClientes.setItems(obList);

         TableView tvApartados = new TableView();
         
        TableColumn<apartado, Integer> idApartadoColumna = new TableColumn<>("Id Apartado");
        idApartadoColumna.setMinWidth(120);
        idApartadoColumna.setCellValueFactory(new PropertyValueFactory<>("id_apartado"));         
  
        TableColumn<apartado, Integer> codigoClienteApaColumna = new TableColumn<>("Codigo Cliente");
        codigoClienteApaColumna.setMinWidth(120);
        codigoClienteApaColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_cliente")); 
        
        TableColumn<apartado, Integer> codigoNotaVentaColumna = new TableColumn<>("Codigo Nota de Venta");
        codigoNotaVentaColumna.setMinWidth(120);
        codigoNotaVentaColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_nota_venta")); 
        
        TableColumn<apartado, String> fechaColumna = new TableColumn<>("Fecha");
        fechaColumna.setMinWidth(120);
        fechaColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        
        TableColumn<apartado, Float> montoColumna = new TableColumn<>("Monto Apartado");
        montoColumna.setMinWidth(120);
        montoColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));
        
        tvApartados.getColumns().addAll(idApartadoColumna, codigoClienteApaColumna, codigoNotaVentaColumna, 
                fechaColumna, montoColumna);
        
        List<String> lstWhereApa = new ArrayList<>();
        
        tvClientes.setOnMouseClicked((event) -> {
            lstPagosApa.clear();
            tfClientes.setText("");
            tfidApartado.setText("");
            tffolio.setText("");
            tfMonto.setText("");
            CLIENTE clTemp = (CLIENTE) tvClientes.getSelectionModel().getSelectedItem();
            lstWhereApa.clear();
            lstWhereApa.add("codigo_cliente = "+clTemp.getCodigo_cliente());
            ObservableList<apartado> obsLstApartado = FXCollections.observableArrayList();
            obsLstApartado.addAll(apaDAO.consultaApartado(lstWhereApa));
            tvApartados.setItems(obsLstApartado);
            lbTotalApartado.setText("$ 0.0");
            lbTotalCubierto.setText("$ 0.0");
            lbResto.setText("$ 0.0");
        });
       vbPpal.setSpacing(5);
       
       btnCancelar.setOnAction((ActionEvent e)->{
           removerVistas();
       });
      
       TableView tvPagos = new TableView();
       
        TableColumn<pagos_apartado, String> folioPagosAparColumna = new TableColumn<>("Folio Remision");
        folioPagosAparColumna.setMinWidth(120);
        folioPagosAparColumna.setCellValueFactory(new PropertyValueFactory<>("folio"));
       
        TableColumn<pagos_apartado, String> fechaPagosAparColumna = new TableColumn<>("Fecha Pago");
        fechaPagosAparColumna.setMinWidth(120);
        fechaPagosAparColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        
        TableColumn<pagos_apartado, String> idPagosAparColumna = new TableColumn<>("id Apartado");
        idPagosAparColumna.setMinWidth(120);
        idPagosAparColumna.setCellValueFactory(new PropertyValueFactory<>("id_apartado"));   
        
        TableColumn<pagos_apartado, Float> montoPagosAparColumna = new TableColumn<>("Monto Pago");
        montoPagosAparColumna.setMinWidth(120);
        montoPagosAparColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));
        
        tvPagos.getColumns().addAll(folioPagosAparColumna, idPagosAparColumna, fechaPagosAparColumna, 
                montoPagosAparColumna);
        tvPagos.setItems(lstPagosApa);

        MenuItem miEliminarPagos = new MenuItem("Eliminar Pago");
        miEliminarPagos.setOnAction((event) -> {
            pagos_apartado pagApaTemp = (pagos_apartado)tvPagos.getSelectionModel().getSelectedItem();
            pagapaDAO.borrarPagosAP(pagApaTemp.getId_pago_ap());
            lstPagosApa.clear();
            lstWhere.clear();
            lstWhere.add("id_apartado = "+pagApaTemp.getId_apartado());
            lstPagosApa = FXCollections.observableArrayList(pagapaDAO.consultaPagosAP(lstWhere));
            tvPagos.setItems(lstPagosApa);
            apartado apaIdent = (apartado) tvApartados.getSelectionModel().getSelectedItem();
            float sumTotApa = apaIdent.getMonto();
            lbTotalApartado.setText(" $ "+String.valueOf(sumTotApa));
            float sumPagado=0;
            for (pagos_apartado pg:lstPagosApa){
              sumPagado = sumPagado + pg.getMonto();
            }
            if (sumPagado > 0) lbTotalCubierto.setText(" $ "+String.valueOf(sumPagado));
            float resta = sumTotApa - sumPagado;
            lbResto.setText(" $ "+String.valueOf(resta));
            
        });
        ContextMenu cmMenuPagos = new ContextMenu();
        cmMenuPagos.getItems().addAll(miEliminarPagos);
        tvPagos.setContextMenu(cmMenuPagos);
        tvApartados.setOnMouseClicked((event) -> {
            apartado apaIdent = (apartado) tvApartados.getSelectionModel().getSelectedItem();
            tfidApartado.setText(String.valueOf(apaIdent.getId_apartado()));
            float sumTotApa = apaIdent.getMonto();
            lbTotalApartado.setText(" $ "+String.valueOf(sumTotApa));
            lstWhere.add("id_apartado = "+apaIdent.getId_apartado());
            lstPagosApa = FXCollections.observableArrayList(pagapaDAO.consultaPagosAP(lstWhere));
            float sumPagado=0;
            for (pagos_apartado pg:lstPagosApa){
              sumPagado = sumPagado + pg.getMonto();
            }
            if (sumPagado > 0){ 
                lbTotalCubierto.setText(" $ "+String.valueOf(sumPagado));
            }
            else lbTotalCubierto.setText(" $ 0.0");
            float resta = sumTotApa - sumPagado;
            lbResto.setText(" $ "+String.valueOf(resta));
            tvPagos.setItems(lstPagosApa);
        });        
        
        MenuItem miVerproductos = new MenuItem("Ver Productos..");
        miVerproductos.setOnAction((event) -> {
            apartado apaTemp = (apartado) tvApartados.getSelectionModel().getSelectedItem();
            ventanaVerProductosComprados(apaTemp.getCodigo_nota_venta());
        }); 
        
        ContextMenu cmMenuCreditos = new ContextMenu(miVerproductos);
        tvApartados.setContextMenu(cmMenuCreditos);
        btnRegPago.setOnAction((event) -> {
            
            apartado apaIdent = (apartado) tvApartados.getSelectionModel().getSelectedItem();
            float sumTotApar = apaIdent.getMonto();
            lbTotalApartado.setText(" $ "+String.valueOf(sumTotApar));
            pagos_apartado pagapa = new pagos_apartado();
            pagapa.setId_apartado(Integer.parseInt(tfidApartado.getText()));
            pagapa.setFolio(tffolio.getText());
            pagapa.setFecha(dpFechaPago.getValue().toString());
            pagapa.setMonto(Float.parseFloat(tfMonto.getText()));
            lstPagosApa.add(pagapa);
            pagapaDAO.insertarPagosAP(pagapa);
            float sumPagado=0;
            for (pagos_apartado pg:lstPagosApa){
              sumPagado = sumPagado + pg.getMonto();
            }
            if (sumPagado > 0) lbTotalCubierto.setText(" $ "+String.valueOf(sumPagado));
            float resta = sumTotApar - sumPagado;
            lbResto.setText(" $ "+String.valueOf(resta));
            tfMonto.setText("");
            tffolio.setText("");
            //tvPagos.setItems(lstPagosCre);
            Alert altMensaje =new Alert(Alert.AlertType.INFORMATION);
            altMensaje.setContentText("Pago a credito Registrado");
            altMensaje.setTitle("Informacion-Credito");
            altMensaje.show();           
        });
        
       VBox vbIzq = new VBox();
       vbIzq.setSpacing(10);
       vbIzq.setAlignment(Pos.CENTER_LEFT);
       
       VBox vbDer = new VBox();
       vbDer.setSpacing(10);
       vbDer.setAlignment(Pos.TOP_LEFT);
       
       vbIzq.getChildren().addAll(lbTablaClientes, tvClientes, lbTablaApartados, tvApartados);
       vbDer.getChildren().addAll(lbTablaPagos, tvPagos, gpDatosPago);
       HBox hbCenter = new HBox(vbIzq, vbDer);
       hbCenter.setSpacing(10);
       vbPpal.getChildren().addAll(lbTituloVista, hbCenter);       
        
        return vbPpal;
    }
    private VBox vistaNuevoProveedor(){
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("AGREGAR PROVEEDOR");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        proveedorDAO prvDAO = new proveedorDAO();
        
         Label lbNombreProveedor = new Label("Nombre : ");
         Label lbTelefonoProveedor = new Label("Telefono: ");
         
         
         TextField tfNombreProveedor = new TextField();
         tfNombreProveedor.setPrefWidth(320);
         TextField tfTelefonoProveedor = new TextField();
         tfTelefonoProveedor.setMaxWidth(200);
         
         Button btnCancelar = new Button("Cancelar");
         btnCancelar.setOnAction((ActionEvent e)->{
             if(vbAreaTrabajo.getChildren().size()>=0){
                 vbAreaTrabajo.getChildren().remove(0);
             }
         });
         
         Button btnGuardar = new Button("Guardar");
         btnGuardar.setOnAction((ActionEvent e)->{
             prvDAO.insertarProveedor(tfNombreProveedor.getText(), tfTelefonoProveedor.getText());
             removerVistas(); //RvbAreaTrabajo.getChildren().remove(0);
         });
         
         HBox hbBotones = new HBox(btnCancelar, btnGuardar);
         hbBotones.setSpacing(10);
        
         GridPane gpPrincipal = new GridPane();
         gpPrincipal.setPadding(new Insets(5, 5, 5, 5));
         gpPrincipal.setHgap(10);
         gpPrincipal.setVgap(10);
         
         gpPrincipal.add(lbNombreProveedor, 0, 1);
         gpPrincipal.add(tfNombreProveedor, 1, 1);
         gpPrincipal.add(lbTelefonoProveedor, 0, 2);
         gpPrincipal.add(tfTelefonoProveedor, 1, 2);
         gpPrincipal.add(hbBotones, 1, 3);
         
         vbPpal.getChildren().addAll(lbTituloVista, gpPrincipal);
        return vbPpal;
    }
    private VBox vistaModificarProveedor (){
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("MODIFICAR PROVEEDOR");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        List<PROVEEDOR> lstProv = new ArrayList();
        List<String> lstWhere = new ArrayList();
        lstWhere.add("codigo_prov is not null");
        obList.setAll(provDAO.consultaProveedores(lstWhere));
        tvProveedores = new TableView();

        TableColumn codProvColumna = new TableColumn("Codigo Proveedor");
        codProvColumna.setMinWidth(120);
        codProvColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prov"));
        
        TableColumn nombreColumna = new TableColumn("Nombre");
        nombreColumna.setMinWidth(320);
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn telColumna = new TableColumn("Telefono");
        telColumna.setMinWidth(220);
        telColumna.setCellValueFactory(new PropertyValueFactory<>("telefono"));
       
        tvProveedores.getColumns().addAll(codProvColumna, nombreColumna, telColumna);
        tvProveedores.setItems(obList);
        
         Label lbCodigoProveedor = new Label("Codigo Proveedor: ");
         Label lbNombreProveedor = new Label("Nombre : ");
         Label lbTelefonoProveedor = new Label("Telefono: ");
         
         TextField tfCodigoProveedor = new TextField();
         tfCodigoProveedor.setPrefWidth(80);
         tfCodigoProveedor.setMaxWidth(80);
         TextField tfNombreProveedor = new TextField();
         tfNombreProveedor.setPrefWidth(320);
         TextField tfTelefonoProveedor = new TextField();
         tfTelefonoProveedor.setMaxWidth(200);
         
         Button btnCancelar = new Button("Cancelar");
         btnCancelar.setOnAction((ActionEvent e)->{
             if(vbAreaTrabajo.getChildren().size()>=0){
                 vbAreaTrabajo.getChildren().remove(0);
             }
         });
         
         Button btnModificar = new Button("Modificar");
         btnModificar.setOnAction((ActionEvent e)->{
           proveedorDAO prvDAO = new proveedorDAO();
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setHeaderText(null);
           alert.setTitle("Confirmación");
           alert.setContentText("¿Estas seguro de confirmar la acción?");
           Optional<ButtonType> action = alert.showAndWait(); 
           if (action.get() == ButtonType.OK) {
              prvDAO.modificarProveedor(Integer.parseInt(tfCodigoProveedor.getText()), tfNombreProveedor.getText(), tfTelefonoProveedor.getText());
              removerVistas();
           } else {
              removerVistas();
           }            
            
         });
         
        tvProveedores.setOnMouseClicked((event) -> {
          //tfNombreProveedor.setText("Seleccionaste: "+ tvProveedores.getSelectionModel().getSelectedIndex());
          PROVEEDOR prvItem = (PROVEEDOR)tvProveedores.getSelectionModel().getSelectedItem();
          tfCodigoProveedor.setText(String.valueOf(prvItem.getCodigo_prov()));
          tfNombreProveedor.setText(prvItem.getNombre());
          tfTelefonoProveedor.setText(prvItem.getTelefono());
         
        });
         
         HBox hbBotones = new HBox(btnCancelar, btnModificar);
         hbBotones.setSpacing(10);
        
         GridPane gpPrincipal = new GridPane();
         gpPrincipal.setPadding(new Insets(5, 5, 5, 5));
         gpPrincipal.setHgap(10);
         gpPrincipal.setVgap(10);
         
         gpPrincipal.add(lbCodigoProveedor, 0, 0);
         gpPrincipal.add(tfCodigoProveedor, 1, 0);
         gpPrincipal.add(lbNombreProveedor, 0, 1);
         gpPrincipal.add(tfNombreProveedor, 1, 1);
         gpPrincipal.add(lbTelefonoProveedor, 0, 2);
         gpPrincipal.add(tfTelefonoProveedor, 1, 2);
         gpPrincipal.add(hbBotones, 1, 3);
        
         vbPpal.getChildren().addAll(lbTituloVista, tvProveedores, gpPrincipal);
        return vbPpal;    
    }
    private VBox vistaEliminarProveedor(){
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("ELIMINAR PROVEEDOR");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        List<PROVEEDOR> lstProv = new ArrayList();
        List<String> lstWhere = new ArrayList();
        lstWhere.add("codigo_prov is not null");
        obList.setAll(provDAO.consultaProveedores(lstWhere));
        TableView tvProveedores = new TableView();
        TableColumn codProvColumna = new TableColumn("Codigo Proveedor");
        codProvColumna.setMinWidth(120);
        codProvColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prov"));
        
        TableColumn nombreColumna = new TableColumn("Nombre");
        nombreColumna.setMinWidth(320);
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn telColumna = new TableColumn("Telefono");
        telColumna.setMinWidth(220);
        telColumna.setCellValueFactory(new PropertyValueFactory<>("telefono"));
       
        tvProveedores.getColumns().addAll(codProvColumna, nombreColumna, telColumna);
        tvProveedores.setItems(obList);
        
         Label lbCodigoProveedor = new Label("Codigo Proveedor: ");
         Label lbNombreProveedor = new Label("Nombre : ");
         Label lbTelefonoProveedor = new Label("Telefono: ");
         
         TextField tfCodigoProveedor = new TextField();
         tfCodigoProveedor.setPrefWidth(80);
         tfCodigoProveedor.setMaxWidth(80);
         TextField tfNombreProveedor = new TextField();
         tfNombreProveedor.setPrefWidth(320);
         TextField tfTelefonoProveedor = new TextField();
         tfTelefonoProveedor.setMaxWidth(200);
         
         Button btnCancelar = new Button("Cancelar");
         btnCancelar.setOnAction((ActionEvent e)->{
             if(vbAreaTrabajo.getChildren().size()>=0){
                 vbAreaTrabajo.getChildren().remove(0);
             }
             
         });
         
         Button btnEliminar = new Button("Eliminar");
         btnEliminar.setOnAction((ActionEvent e)->{
           proveedorDAO prvDAO = new proveedorDAO();
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setHeaderText(null);
           alert.setTitle("Confirmación");
           alert.setContentText("¿Estas seguro de confirmar la acción?");
           Optional<ButtonType> action = alert.showAndWait(); 
           if (action.get() == ButtonType.OK) {
              prvDAO.eliminarLogicamenteProveedor(Integer.parseInt(tfCodigoProveedor.getText()));
              removerVistas();
           } else {
              removerVistas();
           }
         });
         
        tvProveedores.setOnMouseClicked((event) -> {
          //tfNombreProveedor.setText("Seleccionaste: "+ tvProveedores.getSelectionModel().getSelectedIndex());
          PROVEEDOR prvItem = (PROVEEDOR)tvProveedores.getSelectionModel().getSelectedItem();
          tfCodigoProveedor.setText(String.valueOf(prvItem.getCodigo_prov()));
          tfNombreProveedor.setText(prvItem.getNombre());
          tfTelefonoProveedor.setText(prvItem.getTelefono());
         
        });
         
         HBox hbBotones = new HBox(btnCancelar, btnEliminar);
         hbBotones.setSpacing(10);
        
         GridPane gpPrincipal = new GridPane();
         gpPrincipal.setPadding(new Insets(5, 5, 5, 5));
         gpPrincipal.setHgap(10);
         gpPrincipal.setVgap(10);
         
         gpPrincipal.add(lbCodigoProveedor, 0, 0);
         gpPrincipal.add(tfCodigoProveedor, 1, 0);
         gpPrincipal.add(lbNombreProveedor, 0, 1);
         gpPrincipal.add(tfNombreProveedor, 1, 1);
         gpPrincipal.add(lbTelefonoProveedor, 0, 2);
         gpPrincipal.add(tfTelefonoProveedor, 1, 2);
         gpPrincipal.add(hbBotones, 1, 3);
        
         vbPpal.getChildren().addAll(lbTituloVista, tvProveedores, gpPrincipal);
        return vbPpal;    
    
    }
    private VBox vistaGenerarReporteGeneral(){
        
        TableView tvAcumVentasMes = new TableView();
        TableView tvSumVentasdiarias = new TableView();
        TableView tvGastosDiarios = new TableView();
        TableView tvVentasDiarias = new TableView();
        TableView tvPagosCreditos = new TableView();
        TableView tvPagosApartados = new TableView();
        
        Label EtisumTotalVentDia = new Label("0.0");
        EtisumTotalVentDia.setAlignment(Pos.CENTER_RIGHT);
        EtisumTotalVentDia.setMaxWidth(230);
        
        Label EtisumTotalVentMes = new Label("0.0");
        EtisumTotalVentMes.setAlignment(Pos.CENTER_RIGHT);
        EtisumTotalVentMes.setMaxWidth(230);
        
        Label EtisumTotalGastos = new Label("0.0");
        EtisumTotalGastos.setAlignment(Pos.CENTER_RIGHT);
        EtisumTotalGastos.setMaxWidth(230);
        
        Label EtisumTotalCreditos = new Label("0.0");
        EtisumTotalCreditos.setAlignment(Pos.CENTER_RIGHT);
        EtisumTotalCreditos.setMaxWidth(230);
        
        Label EtisumTotalApartados = new Label("0.0");
        EtisumTotalApartados.setAlignment(Pos.CENTER_RIGHT);
        EtisumTotalApartados.setMaxWidth(230);
        
        Label Etiventasdiarias = new Label("0.0");
        Etiventasdiarias.setAlignment(Pos.CENTER_RIGHT);
        Etiventasdiarias.setMaxWidth(750);
        
        VBox vbPpal= new VBox();
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("GENERAR REPORTE ESTADO DIARIO");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        Label lbFecha = new Label("Fecha de los Datos: ");
        DatePicker dpFecha = new DatePicker(LocalDate.now());
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction((ActionEvent e)->{
            if (vbAreaTrabajo.getChildren().size()>=0){
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        
        Button btnGenerar = new Button("Generar ..");
        btnGenerar.setOnAction((event) -> {
         try{
            
           /* User home directory location */
            //String userHomeDirectory = System.getProperty("user.home");
            /* Output file location */
            
            File file;
            JasperReport jasperReport;
            file = new File("Reportes/Formatos/reporteDiarioGeneral.jasper");
            jasperReport = (JasperReport) JRLoader.loadObject(file);
            LocalDateTime ld = LocalDateTime.now();
            String fechaFile = String.valueOf(ld.getDayOfMonth())+String.valueOf(ld.getMonth())+String.valueOf(ld.getYear())+String.valueOf(ld.getHour())+String.valueOf(ld.getMinute())+String.valueOf(ld.getSecond());
            //String outputFile = userHomeDirectory + File.separatorChar + "ReporteDiario"+fechaFile+".pdf";
            //String outputFile = userHomeDirectory + File.separatorChar + "ReporteDiario"+fechaFile+".pdf";
            String outputFile = "Reportes/DiarioGeneral/" + File.separatorChar + "ReporteDiario"+fechaFile+".pdf";
           Map<String, Object> parameters = new HashMap<>();
           JRBeanCollectionDataSource itemsAcumDiario = new JRBeanCollectionDataSource(tvSumVentasdiarias.getItems());           
           JRBeanCollectionDataSource itemsAcumMes = new JRBeanCollectionDataSource(tvAcumVentasMes.getItems());           
           JRBeanCollectionDataSource itemsPagosCre = new JRBeanCollectionDataSource(tvPagosCreditos.getItems());           
           JRBeanCollectionDataSource itemsPagosApa = new JRBeanCollectionDataSource(tvPagosApartados.getItems());           
           JRBeanCollectionDataSource itemsGastos = new JRBeanCollectionDataSource(tvGastosDiarios.getItems());           
           JRBeanCollectionDataSource itemsVentas = new JRBeanCollectionDataSource(tvVentasDiarias.getItems());           
            List<String> listWhere = new ArrayList<>();
            listWhere.add("id_suc = 1");
            List<SUCURSAL> sucList = sucDAO.consultaSucursal(listWhere);
            SUCURSAL sucIdent = sucList.get(0);
           parameters.put("itemsdtAcumDiario", itemsAcumDiario);
           parameters.put("itemsdtAcumMes", itemsAcumMes);
           parameters.put("itemsdtPagosCre", itemsPagosCre);
           parameters.put("itemsdtPagosApa", itemsPagosApa);
           parameters.put("itemsdtGastos", itemsGastos);
           parameters.put("itemsdtVentas", itemsVentas);
           parameters.put("TotAcumVentasDiarias", EtisumTotalVentDia.getText());
           parameters.put("TotVentasDiarias", Etiventasdiarias.getText());
           parameters.put("TotGastos", EtisumTotalGastos.getText());
           parameters.put("TotCreditos", EtisumTotalCreditos.getText());
           parameters.put("TotApartados", EtisumTotalApartados.getText());
           parameters.put("TotAcumVentasMes", EtisumTotalVentMes.getText());
           parameters.put("Sucursal", sucIdent.getNombre());
           parameters.put("Fecha", dpFecha.getValue().toString());
           
           /* Generando el PDF */
            //C:\Users\dopcan\Documents\NetBeansProjects\ClasesConsultorio\src\gestionconsultorio
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            /* outputStream to create PDF */
            OutputStream outputStream = new FileOutputStream(new File(outputFile));
            /* Write content to PDF file */
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            outputStream.close();
              Alert resp = new Alert(Alert.AlertType.INFORMATION);
              resp.setTitle("Informacion");
              resp.setContentText("Reporte Generado en Carpera Reportes/DiarioGeneral!! ");
              resp.showAndWait();
            
        } catch (JRException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
                 Logger.getLogger(CRMPLAMAR.class.getName()).log(Level.SEVERE, null, ex);
             }            
        });
        
        Label lbTableTopLeft = new Label("Ventas acumuladas del dia:");
        Label lbTableTopRigth = new Label("Gastos:");
        Label lbTableBottomLeft = new Label("Ventas diarias:");
        Label lbTableBottomRigth = new Label("Acumulado Del Mes:");
        Label lbTablePagosCredito = new Label ("Pagos Credito");
        Label lbTablePagosApartados = new Label ("Pagos Apartado");
        
        //TableView tvSumVentasdiarias = new TableView();
        TableColumn<acumuladosVenta, String> tipoAcumDayColumna = new TableColumn<>("Tipo Operacion");
        tipoAcumDayColumna.setMinWidth(120);
        tipoAcumDayColumna.setCellValueFactory(new PropertyValueFactory<>("tipo_operacion"));        
 
        TableColumn<acumuladosVenta, Float> totalAcumDayColumna = new TableColumn<>("Total");
        totalAcumDayColumna.setMinWidth(120);
        totalAcumDayColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));
        tvSumVentasdiarias.getColumns().addAll(tipoAcumDayColumna, totalAcumDayColumna);
        //tvSumVentasdiarias.set//
        EtisumTotalVentDia.setMaxWidth(230);
        
        //TableView tvGastosDiarios = new TableView();
        
        TableColumn<gasto, String> conceptoGastoColumna = new TableColumn<>("concepto");
        conceptoGastoColumna.setMinWidth(120);
        conceptoGastoColumna.setCellValueFactory(new PropertyValueFactory<>("concepto"));        
        
        TableColumn<gasto, Float> montoGastoColumna = new TableColumn<>("Monto");
        montoGastoColumna.setMinWidth(120);
        montoGastoColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));
        
        tvGastosDiarios.getColumns().addAll(conceptoGastoColumna, montoGastoColumna);
       
        //TableView tvVentasDiarias = new TableView();
        tvVentasDiarias.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        TableColumn<ventasDiarias, String> tipoVentaColumna = new TableColumn<>("Tipo Operacion");
        tipoVentaColumna.setMinWidth(120);
        tipoVentaColumna.setCellValueFactory(new PropertyValueFactory<>("tipo_venta"));       

        TableColumn<ventasDiarias, String> clienteColumna = new TableColumn<>("Cliente");
        clienteColumna.setMinWidth(280);
        clienteColumna.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        
        TableColumn<ventasDiarias, String> codFaColumna = new TableColumn<>("Codigo Factura");
        codFaColumna.setMinWidth(120);
        codFaColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_factura"));
        
        TableColumn<ventasDiarias, String> folColumna = new TableColumn<>("Folio Remision");
        folColumna.setMinWidth(120);
        folColumna.setCellValueFactory(new PropertyValueFactory<>("folio_nota"));
        
        TableColumn<ventasDiarias, Float> mtoColumna = new TableColumn<>("Total");
        mtoColumna.setMinWidth(120);
        mtoColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));
        
        tvVentasDiarias.getColumns().addAll(tipoVentaColumna, clienteColumna, codFaColumna, folColumna, mtoColumna);
        
        TableColumn<ventasDiarias, String> montColumna = new TableColumn<>("Monto");
        montColumna.setMinWidth(120);
        montColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));       
        
        //TableView tvAcumVentasMes = new TableView();
        TableColumn<acumuladosVenta, String> tipoAcumMesColumna = new TableColumn<>("Tipo Operacion");
        tipoAcumMesColumna.setMinWidth(120);
        tipoAcumMesColumna.setCellValueFactory(new PropertyValueFactory<>("tipo_operacion"));        
 
        TableColumn<acumuladosVenta, Float> totalAcumMesColumna = new TableColumn<>("Total");
        totalAcumMesColumna.setMinWidth(120);
        totalAcumMesColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));

        tvAcumVentasMes.getColumns().addAll(tipoAcumMesColumna, totalAcumMesColumna);

        //TableView tvPagosCreditos = new TableView();
        
        TableColumn<pagos_credito, String> folioPagoCreColumna = new TableColumn<>("Folio");
        folioPagoCreColumna.setMinWidth(120);
        folioPagoCreColumna.setCellValueFactory(new PropertyValueFactory<>("folio")); 
        
        TableColumn<pagos_credito, Float> montoPagoCreColumna = new TableColumn<>("Monto");
        montoPagoCreColumna.setMinWidth(120);
        montoPagoCreColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));
        
        tvPagosCreditos.getColumns().addAll(folioPagoCreColumna, montoPagoCreColumna);

        //TableView tvPagosApartados = new TableView();

        TableColumn<pagos_apartado, String> folioPagoApaColumna = new TableColumn<>("Folio");
        folioPagoApaColumna.setMinWidth(120);
        folioPagoApaColumna.setCellValueFactory(new PropertyValueFactory<>("folio")); 
        
        TableColumn<pagos_apartado, Float> montoPagoApaColumna = new TableColumn<>("Monto");
        montoPagoApaColumna.setMinWidth(120);
        montoPagoApaColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));

        tvPagosApartados.getColumns().addAll(folioPagoApaColumna, montoPagoApaColumna);
        
        Button btnConsultar = new Button("Consultar ..");
        btnConsultar.setOnAction((event) -> {
            
            lstPagosApa.clear();
            lstPagosCre.clear();
            lstConceptosGastos.clear();
            lstWhereConcepto.add("fecha = '"+dpFecha.getValue().toString()+"' ");
            tvGastosDiarios.setItems(gasDAO.consultarGasto(lstWhereConcepto));
            ObservableList<gasto> lstGastoTemp = tvGastosDiarios.getItems();
            float AcuGasto= 0.0f;
            for (gasto gst : lstGastoTemp){
                AcuGasto = AcuGasto + gst.getMonto();
            }
            EtisumTotalGastos.setText("$ "+String.valueOf(AcuGasto));

            SumatoriaVentasDiariasBO sumvtasDBO = new SumatoriaVentasDiariasBO();
            tvSumVentasdiarias.setItems(sumvtasDBO.consultaSumNotasRem(dpFecha.getValue().toString()));
            ObservableList<acumuladosVenta> lstventasDiasAcum = tvSumVentasdiarias.getItems();
            float AcuvenD= 0.0f;
            for (acumuladosVenta avd : lstventasDiasAcum){
                AcuvenD = AcuvenD + avd.getMonto();
            }
            EtisumTotalVentDia.setText("$ "+String.valueOf(AcuvenD));

            tvVentasDiarias.setItems(sumvtasDBO.ventasDelDia(dpFecha.getValue().toString()));
            ObservableList<ventasDiarias> lstventasDiarias = tvVentasDiarias.getItems();
            float venDiarias= 0.0f;
            for (ventasDiarias avd : lstventasDiarias){
                venDiarias = venDiarias + avd.getMonto();
            }
            Etiventasdiarias.setText("$ "+String.valueOf(venDiarias));
            
            tvAcumVentasMes.setItems(sumvtasDBO.ventasAcumMes(dpFecha.getValue().toString()));
            ObservableList<acumuladosVenta> lstacumventasMes = tvAcumVentasMes.getItems();
            float acumvenMes= 0.0f;
            for (acumuladosVenta avm : lstacumventasMes){
                acumvenMes = acumvenMes + avm.getMonto();
            }
            EtisumTotalVentMes.setText("$ "+String.valueOf(acumvenMes));
            
            lstWhereConcepto.clear();
            lstWhereConcepto.add("fecha = '"+dpFecha.getValue().toString()+"' ");
            lstPagosCre.clear();
            lstPagosApa.addAll(FXCollections.observableArrayList(pagapaDAO.consultaPagosAP(lstWhereConcepto)));
            tvPagosApartados.setItems(lstPagosApa);
            ObservableList<pagos_apartado> lstPagosApartados = tvPagosApartados.getItems();
            float pagosApart= 0.0f;
            for (pagos_apartado paga : lstPagosApartados){
                pagosApart = pagosApart + paga.getMonto();
            }
            EtisumTotalApartados.setText("$ "+String.valueOf(pagosApart));
            
            lstWhereConcepto.clear();
            lstWhereConcepto.add("fecha = '"+dpFecha.getValue().toString()+"' ");
            lstPagosCre.clear();
            lstPagosCre.addAll(FXCollections.observableArrayList(pagcreDAO.consultaPagoCred(lstWhereConcepto)));
            tvPagosCreditos.setItems(lstPagosCre);
            ObservableList<pagos_credito> lstPagosCreditos = tvPagosCreditos.getItems();
            float pagosCre= 0.0f;
            for (pagos_credito pagc : lstPagosCreditos){
                pagosCre = pagosCre + pagc.getMonto();
            }
            EtisumTotalCreditos.setText("$ "+String.valueOf(pagosCre));
        });        

        HBox hbReporteFecha = new HBox(btnCancelar, btnConsultar, btnGenerar);
        hbReporteFecha.setSpacing(5);
        
        GridPane gpFechaReporte = new GridPane();
        gpFechaReporte.setPadding(new Insets(5, 5, 5, 5));
        gpFechaReporte.setVgap(10);
        gpFechaReporte.setHgap(10);
        
        gpFechaReporte.add(lbFecha, 0, 0);
        gpFechaReporte.add(dpFecha, 1, 0);
        gpFechaReporte.add(hbReporteFecha, 2, 0);       
        
        VBox vbtvTopLeft = new VBox();
        vbtvTopLeft.setSpacing(10);
        VBox vbtvTopRigth = new VBox();
        vbtvTopRigth.setSpacing(10);
        VBox vbtvBottomLeft = new VBox();
        vbtvBottomLeft.setSpacing(10);
        VBox vbtvBottomRigth = new VBox();
        vbtvBottomRigth.setSpacing(10);
        VBox vbTvPagCre = new VBox();
        vbTvPagCre.setSpacing(10);
        VBox vbTvPagApa = new VBox();
        vbTvPagApa.setSpacing(10);
        
        vbtvTopLeft.getChildren().addAll(lbTableTopLeft, tvSumVentasdiarias, EtisumTotalVentDia);
        vbtvTopRigth.getChildren().addAll(lbTableTopRigth, tvGastosDiarios, EtisumTotalGastos);
        vbtvBottomLeft.getChildren().addAll(lbTableBottomLeft, tvVentasDiarias, Etiventasdiarias);
        vbtvBottomRigth.getChildren().addAll(lbTableBottomRigth, tvAcumVentasMes, EtisumTotalVentMes);
        vbTvPagCre.getChildren().addAll(lbTablePagosCredito, tvPagosCreditos, EtisumTotalCreditos);
        vbTvPagApa.getChildren().addAll(lbTablePagosApartados, tvPagosApartados, EtisumTotalApartados);
        
        GridPane gpSup = new GridPane();
        gpSup.setVgap(10);
        gpSup.setHgap(10);
        
        gpSup.add(vbtvTopLeft, 0,0);
        gpSup.add(vbtvTopRigth, 1,0);
        gpSup.add(vbTvPagCre, 2, 0);
        gpSup.add(vbTvPagApa, 3, 0);
        
        GridPane gpInf = new GridPane();
        gpInf.setVgap(10);
        gpInf.setHgap(10);
        gpInf.add(vbtvBottomLeft, 0,0);
        gpInf.add(vbtvBottomRigth, 1,0);
        
        vbPpal.getChildren().addAll(lbTituloVista, gpFechaReporte, gpSup, gpInf);
        
        return vbPpal;
    
    }
    private VBox vistaIdentificarSucursal(){
        
        SUCURSAL sucIdent = new SUCURSAL();
        List<String> listWhere = new ArrayList<>();
        listWhere.add("id_suc = 1");
        List<SUCURSAL> sucList = sucDAO.consultaSucursal(listWhere);
        sucIdent = sucList.get(0);
        
        VBox vbPpal = new VBox();
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("IDENTIFICAR SUCURSAL");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        Label lbTiendaSucursal = new Label("TIENDAS PLAMAR");
        Font fuenteTienda = new Font("Arial Bold", 48);
        lbTiendaSucursal.setFont(fuenteTienda);
        lbTiendaSucursal.setAlignment(Pos.CENTER);
        
        Label lbDireccion = new Label("DIRECCION: ");
        Font fuenteDireccion = new Font("Arial Bold", 48);
        lbDireccion.setFont(fuenteDireccion);
        lbDireccion.setAlignment(Pos.CENTER);
        
        Label lbSucursal = new Label("SUCURSAL: ");
        Font fuenteSucursal = new Font("Arial Bold", 48);
        lbSucursal.setFont(fuenteSucursal);
        lbSucursal.setAlignment(Pos.CENTER);
        
        lbSucursal.setText("Sucursal: "+sucIdent.getNombre());
        lbDireccion.setText("Direccion: "+sucIdent.getDireccion());        
        
        VBox vbIdentificacionColocada = new VBox();
        vbIdentificacionColocada.setAlignment(Pos.CENTER);
        vbIdentificacionColocada.setSpacing(10);
        vbIdentificacionColocada.getChildren().addAll(lbTiendaSucursal, lbSucursal, lbDireccion);
       
        
        Label lbNombreSucursal = new Label("Nombre Sucursal: ");
        Label lbDireccionSucursal = new Label("Dirección Sucursal: ");
        
        TextField tfNombreSucursal = new TextField();
        tfNombreSucursal.setPrefWidth(320);
        
        TextField tfDireccionSucursal = new TextField();
        tfDireccionSucursal.setPrefWidth(320);
        
        
        
        Button btnCancelar = new Button("Salir");
        btnCancelar.setOnAction((ActionEvent e)->{
            if (vbAreaTrabajo.getChildren().size()>0){
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction((ActionEvent e)->{
            SUCURSAL sucIde = new SUCURSAL();
            sucIde.setNombre(tfNombreSucursal.getText().toUpperCase());
            sucIde.setDireccion(tfDireccionSucursal.getText().toUpperCase());
            sucIde.setId_suc(1);

           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setHeaderText(null);
           alert.setTitle("Confirmación");
           alert.setContentText("Actualizar Datos Sucursal?");
           Optional<ButtonType> action = alert.showAndWait(); 
           if (action.get() == ButtonType.OK) {
              sucDAO.modificarSucursal(sucIde);
              lbSucursal.setText("SUCURSAL: "+sucIde.getNombre());
              lbDireccion.setText("DIRECCION: "+sucIde.getDireccion());
//            removerVistas();
           } else {
//              removerVistas();
           }
        });
        
        HBox hbBotones = new HBox(btnCancelar, btnGuardar);
        hbBotones.setSpacing(5);
        
        GridPane gpDatosSucursal = new GridPane();
        gpDatosSucursal.setPadding(new Insets(5, 5, 5, 5));
        gpDatosSucursal.setVgap(10);
        gpDatosSucursal.setHgap(10);
        gpDatosSucursal.add(lbNombreSucursal, 0, 1);
        gpDatosSucursal.add(tfNombreSucursal, 1, 1);
        gpDatosSucursal.add(lbDireccionSucursal, 0, 2);
        gpDatosSucursal.add(tfDireccionSucursal, 1, 2);
        gpDatosSucursal.add(hbBotones, 1, 3);
        
        vbPpal.getChildren().addAll(lbTituloVista, gpDatosSucursal, vbIdentificacionColocada);
        return vbPpal;
    }
    private VBox vistaCrearCuenta(){
        VBox vbPpal = new VBox();
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("CREAR CUENTA DE ACCESO");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);

        
        Label lbNombre = new Label("Nombre: ");
        Label lbTipo = new Label("Tipo: ");
        Label lbUsuario = new Label("Usuario: ");
        Label lbClave = new Label("Clave: ");
        
        TextField tfNombre = new TextField();
        tfNombre.setMaxWidth(320);

        ObservableList<String> oblstTip = FXCollections.observableArrayList("SuperUser","INVENTARIO","VENTA"); 
        ComboBox cbTipo = new ComboBox(oblstTip);
        cbTipo.setMaxWidth(120);
        
        TextField tfUsuario = new TextField();
        tfUsuario.setMaxWidth(120);
        TextField tfClave = new TextField();
        tfClave.setMaxWidth(120);
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction((ActionEvent e)->{
            if (vbAreaTrabajo.getChildren().size()>0){
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction((ActionEvent e)->{
            usuario usr = new usuario();
            usr.setNombre_completo(tfNombre.getText());
            usr.setUsuario(tfUsuario.getText());
            usr.setTipo(cbTipo.getValue().toString());
            usr.setClave(tfClave.getText());
            int idResult = userDAO.insertarUsuario(usr);
            if (idResult != -1){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Confirmación");
                alert.setContentText("Usuario "+idResult+" guardado correctamente");
                alert.showAndWait();
            }
        });
        
        HBox hbBotones = new HBox(btnCancelar, btnGuardar);
        hbBotones.setSpacing(10);
        
        GridPane gpDatosUsuario = new GridPane();
        gpDatosUsuario.setPadding(new Insets(5, 5, 5, 5));
        gpDatosUsuario.setVgap(10);
        gpDatosUsuario.setHgap(10);
        gpDatosUsuario.add(lbTipo, 0, 1);
        gpDatosUsuario.add(cbTipo, 1, 1);
        gpDatosUsuario.add(lbNombre, 0, 2);
        gpDatosUsuario.add(tfNombre, 1, 2);
        gpDatosUsuario.add(lbUsuario, 0, 3);
        gpDatosUsuario.add(tfUsuario, 1, 3);
        gpDatosUsuario.add(lbClave, 0, 4);
        gpDatosUsuario.add(tfClave, 1, 4);
        gpDatosUsuario.add(hbBotones, 1, 5);
        
        vbPpal.getChildren().addAll(lbTituloVista, gpDatosUsuario);
        return vbPpal;
    
    }
    private VBox vistaModificarCuenta(){
        VBox vbPpal = new VBox();
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("MODIFICAR CUENTA DE ACCESO");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);

        TableView tvUsuarios = new TableView();
        
        TableColumn<usuario, Integer> tcId_Usuario = new TableColumn("ID");
        TableColumn<usuario, String> tcUsuario = new TableColumn("USUARIO");
        TableColumn<usuario, String> tcClave = new TableColumn("CLAVE");
        TableColumn<usuario, String> tcNombre = new TableColumn("NOMBRE");
        TableColumn<usuario, String> tcTipo = new TableColumn("TIPO");
        
        tcId_Usuario.setMinWidth(80);
        tcUsuario.setMinWidth(120);
        tcClave.setMinWidth(120);
        tcNombre.setMinWidth(320);
        tcTipo.setMinWidth(120);
        
        tvUsuarios.setMaxSize(760, 400);

        tcId_Usuario.setCellValueFactory(new PropertyValueFactory<>("id_usuario"));        
        tcUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));        
        tcClave.setCellValueFactory(new PropertyValueFactory<>("clave"));        
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre_completo"));        
        tcTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));        
        tvUsuarios.getColumns().addAll(tcId_Usuario, tcUsuario, tcClave, tcNombre, tcTipo);
        
         lstWhereConcepto.add("id_usuario is not null");
         lstUsuarios=FXCollections.observableArrayList(userDAO.consultaUsuario(lstWhereConcepto));
         tvUsuarios.setItems(lstUsuarios);

        
        Label lbNombre = new Label("Nombre: ");
        Label lbTipo = new Label("Tipo: ");
        Label lbUsuario = new Label("Usuario: ");
        Label lbClave = new Label("Clave: ");
        
        TextField tfNombre = new TextField();
        tfNombre.setMaxWidth(320);

        ObservableList<String> oblstTip = FXCollections.observableArrayList("SuperUser","INVENTARIO","VENTA"); 
        ComboBox cbTipo = new ComboBox(oblstTip);
        cbTipo.setMaxWidth(120);
        
        TextField tfUsuario = new TextField();
        tfUsuario.setMaxWidth(120);
        TextField tfClave = new TextField();
        tfClave.setMaxWidth(120);
 
         tvUsuarios.setOnMouseClicked((event) -> {
             usuario usrTemp = (usuario) tvUsuarios.getSelectionModel().getSelectedItem();
             tfNombre.setText(usrTemp.getNombre_completo());
             tfUsuario.setText(usrTemp.getUsuario());
             tfClave.setText(usrTemp.getClave());
             cbTipo.setValue(usrTemp.getTipo());
         });
        
        Button btnSalir = new Button("Salir");
        btnSalir.setOnAction((ActionEvent e)->{
            if (vbAreaTrabajo.getChildren().size()>0){
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        Button btnGuardar = new Button("Modificar");
        btnGuardar.setOnAction((ActionEvent e)->{
            usuario usr = new usuario();
            usr = (usuario) tvUsuarios.getSelectionModel().getSelectedItem();
            usr.setNombre_completo(tfNombre.getText());
            usr.setUsuario(tfUsuario.getText());
            usr.setTipo(cbTipo.getValue().toString());
            usr.setClave(tfClave.getText());
            int idResult = userDAO.modificarUsuario(usr);
            if (idResult != -1){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Confirmación");
                alert.setContentText("Usuario guardado correctamente");
                alert.showAndWait();
                lstWhereConcepto.clear();
                lstWhereConcepto.add("id_usuario is not null");
                lstUsuarios=FXCollections.observableArrayList(userDAO.consultaUsuario(lstWhereConcepto));
                tvUsuarios.setItems(lstUsuarios);
            }
        });
        
        HBox hbBotones = new HBox(btnSalir, btnGuardar);
        hbBotones.setSpacing(10);
        
        GridPane gpDatosUsuario = new GridPane();
        gpDatosUsuario.setPadding(new Insets(5, 5, 5, 5));
        gpDatosUsuario.setMaxWidth(760);
        gpDatosUsuario.setVgap(10);
        gpDatosUsuario.setHgap(10);
        gpDatosUsuario.add(lbTipo, 0, 1);
        gpDatosUsuario.add(cbTipo, 1, 1);
        gpDatosUsuario.add(lbNombre, 0, 2);
        gpDatosUsuario.add(tfNombre, 1, 2);
        gpDatosUsuario.add(lbUsuario, 0, 3);
        gpDatosUsuario.add(tfUsuario, 1, 3);
        gpDatosUsuario.add(lbClave, 0, 4);
        gpDatosUsuario.add(tfClave, 1, 4);
        gpDatosUsuario.add(hbBotones, 1, 5);
        
        vbPpal.getChildren().addAll(lbTituloVista, tvUsuarios, gpDatosUsuario);
        return vbPpal;
    }
    private VBox vistaEliminarCuenta(){
        VBox vbPpal = new VBox();
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("ELIMINAR CUENTA DE ACCESO");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);

        TableView tvUsuarios = new TableView();
        
        TableColumn<usuario, Integer> tcId_Usuario = new TableColumn("ID");
        TableColumn<usuario, String> tcUsuario = new TableColumn("USUARIO");
        TableColumn<usuario, String> tcClave = new TableColumn("CLAVE");
        TableColumn<usuario, String> tcNombre = new TableColumn("NOMBRE");
        TableColumn<usuario, String> tcTipo = new TableColumn("TIPO");
        
        tcId_Usuario.setMinWidth(80);
        tcUsuario.setMinWidth(120);
        tcClave.setMinWidth(120);
        tcNombre.setMinWidth(320);
        tcTipo.setMinWidth(120);
        
        tvUsuarios.setMaxSize(760, 400);

        tcId_Usuario.setCellValueFactory(new PropertyValueFactory<>("id_usuario"));        
        tcUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));        
        tcClave.setCellValueFactory(new PropertyValueFactory<>("clave"));        
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre_completo"));        
        tcTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));        
        tvUsuarios.getColumns().addAll(tcId_Usuario, tcUsuario, tcClave, tcNombre, tcTipo);
        
         lstWhereConcepto.add("id_usuario is not null");
         lstUsuarios=FXCollections.observableArrayList(userDAO.consultaUsuario(lstWhereConcepto));
         tvUsuarios.setItems(lstUsuarios);

        
        Label lbNombre = new Label("Nombre: ");
        Label lbTipo = new Label("Tipo: ");
        Label lbUsuario = new Label("Usuario: ");
        Label lbClave = new Label("Clave: ");
        
        TextField tfNombre = new TextField();
        tfNombre.setMaxWidth(320);

        ObservableList<String> oblstTip = FXCollections.observableArrayList("SuperUser","INVENTARIO","VENTA"); 
        ComboBox cbTipo = new ComboBox(oblstTip);
        cbTipo.setMaxWidth(120);
        
        TextField tfUsuario = new TextField();
        tfUsuario.setMaxWidth(120);
        TextField tfClave = new TextField();
        tfClave.setMaxWidth(120);
 
         tvUsuarios.setOnMouseClicked((event) -> {
             usuario usrTemp = (usuario) tvUsuarios.getSelectionModel().getSelectedItem();
             tfNombre.setText(usrTemp.getNombre_completo());
             tfUsuario.setText(usrTemp.getUsuario());
             tfClave.setText(usrTemp.getClave());
             cbTipo.setValue(usrTemp.getTipo());
         });
        
        Button btnSalir = new Button("Salir");
        btnSalir.setOnAction((ActionEvent e)->{
            if (vbAreaTrabajo.getChildren().size()>0){
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        Button btnEliminar = new Button("Eliminar");
        btnEliminar.setOnAction((ActionEvent e)->{
            usuario usr = new usuario();
            usr = (usuario) tvUsuarios.getSelectionModel().getSelectedItem();
            usr.setNombre_completo(tfNombre.getText());
            usr.setUsuario(tfUsuario.getText());
            usr.setTipo(cbTipo.getValue().toString());
            usr.setClave(tfClave.getText());
            userDAO.borrarUsuario(usr.getId_usuario());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmación");
            alert.setContentText("Usuario Eliminado correctamente");
            alert.showAndWait();
            tvUsuarios.getItems().remove(tvUsuarios.getSelectionModel().getSelectedIndex());
        });
        
        HBox hbBotones = new HBox(btnSalir, btnEliminar);
        hbBotones.setSpacing(10);
        
        GridPane gpDatosUsuario = new GridPane();
        gpDatosUsuario.setPadding(new Insets(5, 5, 5, 5));
        gpDatosUsuario.setMaxWidth(760);
        gpDatosUsuario.setVgap(10);
        gpDatosUsuario.setHgap(10);
        gpDatosUsuario.add(lbTipo, 0, 1);
        gpDatosUsuario.add(cbTipo, 1, 1);
        gpDatosUsuario.add(lbNombre, 0, 2);
        gpDatosUsuario.add(tfNombre, 1, 2);
        gpDatosUsuario.add(lbUsuario, 0, 3);
        gpDatosUsuario.add(tfUsuario, 1, 3);
        gpDatosUsuario.add(lbClave, 0, 4);
        gpDatosUsuario.add(tfClave, 1, 4);
        gpDatosUsuario.add(hbBotones, 1, 5);
        
        vbPpal.getChildren().addAll(lbTituloVista, tvUsuarios, gpDatosUsuario);
        return vbPpal;
    
    }
    private VBox vistaNuevaCompra(){
        Label lbTituloVista = new Label("REGISTRAR COMPRAS");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        if (!lstDetcompra.isEmpty()){lstDetcompra.clear();}
        
        VBox vbVistaPpal = new VBox();
        
        Label lbFechaCompra = new Label("Fecha Compra: ");
        Label lbFolioNotaCompra = new Label("Folio Nota Compra: ");
	Label lbCantidad  = new Label("Cantidad: ");
	Label lbCodigoProd  = new Label("Codigo Producto: ");
        Label lbDescProducto = new Label ("Descripción Producto: ");
        Label lbCostoCompra = new Label ("Costo Compra Unitario: ");
        Label lbCodigoFactura = new Label ("Codigo Factura: ");
        
        
        DatePicker dpFecha = new DatePicker(LocalDate.now());
        TextField tfFolioNotaCompra = new TextField();
        TextField tfCostoCompra = new TextField();
        tfCostoCompra.setMaxWidth(80);
        TextField tfCantidad = new TextField();
        tfCantidad.setMaxWidth(80);
        TextField tfCodigoFactura = new TextField();
        TextField tfCodigoProducto = new TextField();
        TextField tfDescrProd = new TextField();
        tfDescrProd.setMaxWidth(300);
        tfDescrProd.setPrefWidth(300);
        
        //Componentes de Interfaz
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgBusquedas = new ToggleGroup();
        
        RadioButton rbTodos = new RadioButton("Todos");
        RadioButton rbCodigo = new RadioButton("Codigo");
        RadioButton rbDescripcion = new RadioButton("Descripción");
        RadioButton rbCategoria = new RadioButton("Categoria");
        rbTodos.setSelected(true);
        
        rbTodos.setToggleGroup(tgBusquedas);
        rbCodigo.setToggleGroup(tgBusquedas);
        rbDescripcion.setToggleGroup(tgBusquedas);
        rbCategoria.setToggleGroup(tgBusquedas);
        
       List<String> lstCategorias = new ArrayList<>();
       List<String> lstWherecat = new ArrayList<>();
       lstWherecat.add("id_categoria is not null");
       for (categoria i : categDAO.consultarCategoria(lstWherecat)){
            lstCategorias.add(i.getCategoria());
       }
        
        Label lbCodigo = new Label("Codigo: ");
        TextField tfCodigo = new TextField();
        Label lbDescripcion = new Label("Descripción: ");
        TextField tfDescripcion = new TextField();
        Label lbCategoria = new Label("Categoria: ");
        ComboBox cbCategoria = new ComboBox(FXCollections.observableArrayList(lstCategorias));
        cbCategoria.setPrefWidth(140);
        
        Label lbInventario = new Label("Tabla Inventario: ");
        TableView tvInventario = new TableView();
        tvInventario.setPrefHeight(350);
        tvInventario.setPrefWidth(550);
        
        TableColumn<inventario, Integer> claveProdColumna = new TableColumn<>("Codigo Producto");
        claveProdColumna.setMinWidth(120);
        claveProdColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));

        TableColumn<inventario, Integer> existenciaColumna = new TableColumn<>("Existencia");
        existenciaColumna.setMinWidth(120);
        existenciaColumna.setCellValueFactory(new PropertyValueFactory<>("existencia"));
        
        TableColumn<inventario, Integer> idUbicacionColumna = new TableColumn<>("Id Ubicación");
        idUbicacionColumna.setMinWidth(120);
        idUbicacionColumna.setCellValueFactory(new PropertyValueFactory<>("id_ubicacion"));
        
        TableColumn<inventario, Float> pMenudeoColumna = new TableColumn<>("Precio Menudeo");
        pMenudeoColumna.setMinWidth(120);
        pMenudeoColumna.setCellValueFactory(new PropertyValueFactory<>("precio_menudeo"));        

        TableColumn<inventario, Float> pMayoreoColumna = new TableColumn<>("Precio Mayoreo");
        pMayoreoColumna.setMinWidth(120);
        pMayoreoColumna.setCellValueFactory(new PropertyValueFactory<>("precio_mayoreo"));
        
        TableColumn<inventario, String> descripcionColumna = new TableColumn<>("Descripción");
        descripcionColumna.setMinWidth(120);
        descripcionColumna.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        
        TableColumn<inventario, String> uMedidaColumna = new TableColumn<>("Unidad Medidad");
        uMedidaColumna.setMinWidth(120);
        uMedidaColumna.setCellValueFactory(new PropertyValueFactory<>("unidad_medida"));

        TableColumn<inventario, Float> cCompraColumna = new TableColumn<>("Costo Compra");
        cCompraColumna.setMinWidth(120);
        cCompraColumna.setCellValueFactory(new PropertyValueFactory<>("costo_compra"));
        
        TableColumn<inventario, Integer> codProvColumna = new TableColumn<>("Codigo Proveedor");
        codProvColumna.setMinWidth(120);
        codProvColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prov"));
        
        tvInventario.getColumns().addAll(claveProdColumna, descripcionColumna, existenciaColumna,
                pMenudeoColumna, pMayoreoColumna, uMedidaColumna, idUbicacionColumna, 
                codProvColumna, cCompraColumna);
        List<String> lstWhere = new ArrayList<>();
        lstWhere.add("codigo_prod is not null");
        tvInventario.setItems(invent.consultarInventario(lstWhere));
        
        tvInventario.setOnMouseClicked((event) -> {
            inventario inv = new inventario();
            inv = (inventario) tvInventario.getSelectionModel().getSelectedItem();
            tfCodigoProducto.setText(String.valueOf(inv.getCodigo_prod()));
            tfDescrProd.setText(inv.getDescripcion());
        });
        Label lbProducto = new Label("Tabla Producto Comprados: ");
        TableView tvProductosSelecc = new TableView();
        tvProductosSelecc.setPrefHeight(350);
        tvProductosSelecc.setPrefWidth(550);
        
        //id_detalle_compra, id_compra, codigo_prod, cantidad, costo_compra, bandera
        
        TableColumn<detalle_compra, Integer> codigoProCompColumna = new TableColumn<>("Codigo Producto");
        codigoProCompColumna.setMinWidth(80);
        codigoProCompColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));
        
        TableColumn<detalle_compra, String> descrProCompColumna = new TableColumn<>("Descripción Producto");
        descrProCompColumna.setMinWidth(220);
        descrProCompColumna.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        
        TableColumn<detalle_compra, Integer> cantidadProCompColumna = new TableColumn<>("Cantidad");
        cantidadProCompColumna.setMinWidth(80);
        cantidadProCompColumna.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        
        TableColumn<detalle_compra, Integer> costoProCompColumna = new TableColumn<>("Costo Compra");
        costoProCompColumna.setMinWidth(80);
        costoProCompColumna.setCellValueFactory(new PropertyValueFactory<>("costo_compra"));
              
        tvProductosSelecc.getColumns().addAll(codigoProCompColumna, descrProCompColumna, cantidadProCompColumna, costoProCompColumna);
        tvProductosSelecc.setItems(lstDetcompra);

        Button btnAgregarProducto = new Button("Agregar Producto");
        btnAgregarProducto.setOnAction((ActionEvent e)->{
            detalle_compra det_Compra = new detalle_compra();
            det_Compra.setCodigo_prod(Integer.parseInt(tfCodigoProducto.getText()));
            det_Compra.setDescripcion(tfDescrProd.getText());
            det_Compra.setCantidad(Integer.parseInt(tfCantidad.getText()));
            float fcosto = Float.valueOf(tfCostoCompra.getText()); 
            det_Compra.setCosto_compra(fcosto);
            lstDetcompra.add(det_Compra);
        });
        
        MenuItem miEliminaProSelec = new MenuItem("Eliminar");
        miEliminaProSelec.setOnAction((event) -> {
            tvProductosSelecc.getItems().remove(
                    tvProductosSelecc.getSelectionModel().getSelectedIndex());
        });
        ContextMenu cmOpcionEliminar = new ContextMenu();
        cmOpcionEliminar.getItems().add(miEliminaProSelec);
        tvProductosSelecc.setContextMenu(cmOpcionEliminar);
        
        VBox vbHead = new VBox();

        HBox hbTipoSeleccion = new HBox();
        hbTipoSeleccion.getChildren().addAll(rbTodos, rbCodigo, rbDescripcion, rbCategoria);
        hbTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        hbTipoSeleccion.setSpacing(5);
        
        VBox vbCodigo = new VBox();
        vbCodigo.getChildren().addAll(lbCodigo, tfCodigo);
        vbCodigo.setSpacing(5);
        
        VBox vbDesc = new VBox();
        vbDesc.getChildren().addAll(lbDescripcion, tfDescripcion);
        vbDesc.setSpacing(5);
        
        VBox vbCat = new VBox();
        vbCat.getChildren().addAll(lbCategoria, cbCategoria);
        vbCat.setSpacing(5);
        
        
        HBox hbCompSeleccion = new HBox();
        Button btnBuscarProductos = new Button("Seleccionar");
        btnBuscarProductos.setMaxHeight(50);
        btnBuscarProductos.setOnAction((ActionEvent e)->{
         List<inventario> lstInv = new ArrayList<>();
         List<String> lstWherelc = new ArrayList<>();
         
          if (rbTodos.isSelected()){
           lstWhere.clear();
           lstWherelc.add("codigo_prod is not null");
           tvInventario.setItems(invent.consultarInventario(lstWherelc));
         } 
         
         if (rbCodigo.isSelected()){
           lstWhere.clear();
           lstWhere.add("codigo_prod = "+tfCodigo.getText());
           tvInventario.setItems(invent.consultarInventario(lstWhere));
         }
         if (rbDescripcion.isSelected()){
           lstWhere.clear();             
           lstWherelc.add("descripcion like '%"+tfDescripcion.getText()+"%' ");
           tvInventario.setItems(invent.consultarInventario(lstWherelc));
         }
         
         if (rbCategoria.isSelected()){
           lstWhere.clear();
           lstWherecat.add("id_categoria is not null");  
           for (categoria i : categDAO.consultarCategoria(lstWherecat)){
             String strCategoria =  i.getCategoria();
             if (strCategoria.compareTo(cbCategoria.getSelectionModel().getSelectedItem().toString())==0){
                  lstWhere.add("id_categoria = "+i.getId_categoria());
                  tvInventario.setItems(invent.consultarInventario(lstWhere));
             }
           }
         }
        });
        
        hbCompSeleccion.getChildren().addAll(vbCodigo, vbDesc, vbCat, btnBuscarProductos);
        hbCompSeleccion.setPadding(new Insets(10, 10, 10, 10));
        hbCompSeleccion.setSpacing(5);

        Separator spSeleccionProductos = new Separator();
        vbHead.getChildren().addAll(lbTipoBusqueda, hbTipoSeleccion, hbCompSeleccion, spSeleccionProductos);
        
        VBox vbTabInventario = new VBox();
        vbTabInventario.setPadding(new Insets(5, 5, 5, 5));
        
        vbTabInventario.getChildren().addAll(lbInventario, tvInventario, lbProducto, tvProductosSelecc);
        
        GridPane gpBloqueProducto = new GridPane();
        gpBloqueProducto.setPadding(new Insets(5, 5, 5, 5));
        gpBloqueProducto.setVgap(10);
        gpBloqueProducto.setHgap(10);
        
        gpBloqueProducto.add(lbFechaCompra , 0, 0);
        gpBloqueProducto.add(dpFecha , 1, 0);
        
        gpBloqueProducto.add(lbFolioNotaCompra , 2, 0);
        gpBloqueProducto.add(tfFolioNotaCompra , 3, 0);
        
        gpBloqueProducto.add(lbCodigoProd , 0, 1);
        gpBloqueProducto.add(tfCodigoProducto , 1, 1);
        
        gpBloqueProducto.add(lbDescProducto , 2, 1);
        gpBloqueProducto.add(tfDescrProd , 3, 1);
        
        gpBloqueProducto.add(lbCantidad , 0, 2);
        gpBloqueProducto.add(tfCantidad , 1, 2);
        
        gpBloqueProducto.add(lbCodigoFactura , 2, 2);
        gpBloqueProducto.add(tfCodigoFactura , 3, 2);
        
        gpBloqueProducto.add(lbCostoCompra , 0, 3);
        gpBloqueProducto.add(tfCostoCompra , 1, 3);
        
        gpBloqueProducto.add(btnAgregarProducto, 3, 3);

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        
        Button btnGuardar = new Button("Guardar y Actualizar Existencias");
        btnGuardar.setOnAction((event) -> {
              int idCompReg=0;
              compraDAO compDAO = new compraDAO();
              detalle_compraDAO detcompDAO = new detalle_compraDAO();

              compra comp = new compra();
              comp.setCodigo_factura(tfCodigoFactura.getText());
              comp.setFecha(dpFecha.getValue().toString());
              comp.setFolio(tfFolioNotaCompra.getText());
              idCompReg=compDAO.insertarCompra(comp);
                for (int i =0; i<lstDetcompra.size(); i++){
                  detalle_compra detCom = lstDetcompra.get(i);
                  detCom.setId_compra(idCompReg);
                  detcompDAO.insertarDetComp(detCom);
                  lstWhere.add("codigo_prod= "+detCom.getCodigo_prod());
                  List<inventario> lstiv = invent.consultaInventario(lstWhere);
                  inventario iv = lstiv.get(0);
                  int existenciaProd = iv.getExistencia();
                  int nuevaExistenciaProd = existenciaProd + detCom.getCantidad();
                  invent.modificarExistenciaProducto(detCom.getCodigo_prod(), nuevaExistenciaProd);               
                }
                Alert altMensaje = new Alert(Alert.AlertType.INFORMATION);
                altMensaje.setContentText("Compra de Productos Registrada");
                altMensaje.setTitle("Informacion-Compra");
                altMensaje.show(); 
                lstDetcompra.clear();
                tfCantidad.setText("");
                tfCodigo.setText("");
                tfCodigoFactura.setText("");
                tfCodigoProducto.setText("");
                tfCostoCompra.setText("");
                tfDescrProd.setText("");
                tfFolioNotaCompra.setText("");
        });
        
        HBox hbBotonesInferiores = new HBox();
        hbBotonesInferiores.setAlignment(Pos.CENTER_RIGHT);
        hbBotonesInferiores.getChildren().addAll(btnCancelar, btnGuardar);
        
        VBox vbTabProducto = new VBox();
        vbTabProducto.setSpacing(5);
        vbTabProducto.getChildren().addAll(gpBloqueProducto, hbBotonesInferiores);
        
        HBox hbBody = new HBox();
        hbBody.getChildren().addAll(vbTabInventario, vbTabProducto);
        
        vbVistaPpal.getChildren().addAll(lbTituloVista, vbHead,hbBody);
        
        return vbVistaPpal; 
    }
    private VBox vistaModificarConsultarCompra(){
        Label lbTituloVista = new Label("Consultar/Modificar COMPRAS");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        if (lstCompra.size()>0){
            //lstCompra = FXCollections.observableArrayList();
            lstCompra.clear();
        }
        if (!lstDetcompra.isEmpty()){lstDetcompra.clear();}
        
        VBox vbVistaPpal = new VBox();
        
        Label lbFechaCompra = new Label("Fecha Compra: ");
        Label lbFolioNotaCompra = new Label("Folio Nota Compra: ");
	Label lbCantidad  = new Label("Cantidad: ");
	Label lbCodigoProd  = new Label("Codigo Producto: ");
        Label lbDescProducto = new Label ("Descripción Producto: ");
        Label lbCostoCompra = new Label ("Costo Compra Unitario: ");
        Label lbCodigoFactura = new Label ("Codigo Factura: ");
        
        
        DatePicker dpFecha = new DatePicker(LocalDate.now());
        TextField tfFolioNotaCompra = new TextField();
        TextField tfCostoCompra = new TextField();
        tfCostoCompra.setMaxWidth(80);
        TextField tfCantidad = new TextField();
        tfCantidad.setMaxWidth(80);
        TextField tfCodigoFactura = new TextField();
        TextField tfCodigoProducto = new TextField();
        TextField tfDescrProd = new TextField();
        tfDescrProd.setMaxWidth(300);
        tfDescrProd.setPrefWidth(300);
        
        //Componentes de Interfaz
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgBusquedas = new ToggleGroup();
        
        RadioButton rbTodos = new RadioButton("Todos");
        RadioButton rbCodigoFactura = new RadioButton("Codigo Factura");
        RadioButton rbFolio = new RadioButton("Folio Nota Remision");
        RadioButton rbFecha = new RadioButton("Fecha Compra");
        rbFecha.setSelected(true);
        
        rbTodos.setToggleGroup(tgBusquedas);
        rbCodigoFactura.setToggleGroup(tgBusquedas);
        rbFolio.setToggleGroup(tgBusquedas);
        rbFecha.setToggleGroup(tgBusquedas);
               
        Label lbCodigo = new Label("Codigo Factura: ");
        TextField tfCodigo = new TextField();
        Label lbFolioNota = new Label("Folio Nota Remision: ");
        TextField tfFolio = new TextField();
        Label lbFecha = new Label("Fecha Compra:");
        DatePicker dpFechaCompra = new DatePicker(LocalDate.now());
        
        
        Label lbCompras = new Label("Tabla Compras: ");
        TableView tvCompras = new TableView();
        tvCompras.setPrefHeight(350);
        tvCompras.setPrefWidth(550);
        
        TableColumn<compra, Integer> idCompraColumna = new TableColumn<>("Id");
        idCompraColumna.setMinWidth(120);
        idCompraColumna.setCellValueFactory(new PropertyValueFactory<>("id_compra"));

        TableColumn<compra, String> fechaColumna = new TableColumn<>("Fecha");
        fechaColumna.setMinWidth(120);
        fechaColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        
        TableColumn<compra, String> codFacColumna = new TableColumn<>("Codigo Factura");
        codFacColumna.setMinWidth(120);
        codFacColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_factura"));
        
        TableColumn<compra, String> folioRemisionColumna = new TableColumn<>("Folio Remision");
        folioRemisionColumna.setMinWidth(120);
        folioRemisionColumna.setCellValueFactory(new PropertyValueFactory<>("folio"));        

        tvCompras.getColumns().addAll(idCompraColumna, fechaColumna, codFacColumna,
                folioRemisionColumna);
        
        LocalDate ldToday = dpFechaCompra.getValue();

        List<String> lstWhere = new ArrayList<>();
        lstWhere.clear();
        lstWhere.add("fecha = '"+ldToday.toString()+"' ");
        //lstWhere.add("id_compra is not null");
        lstCompra = FXCollections.observableArrayList(compDAO.consultaCompra(lstWhere));
        tvCompras.setItems(lstCompra);
        
        Label lbProducto = new Label("Tabla Producto Comprados: ");
        TableView tvProductosSelecc = new TableView();
        tvProductosSelecc.setPrefHeight(350);
        tvProductosSelecc.setPrefWidth(550);
        
        TableColumn<detalle_compra, Integer> codigoProCompColumna = new TableColumn<>("Codigo Producto");
        codigoProCompColumna.setMinWidth(80);
        codigoProCompColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));
        
        TableColumn<detalle_compra, String> descrProCompColumna = new TableColumn<>("Descripción Producto");
        descrProCompColumna.setMinWidth(220);
        descrProCompColumna.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        
        TableColumn<detalle_compra, Integer> cantidadProCompColumna = new TableColumn<>("Cantidad");
        cantidadProCompColumna.setMinWidth(80);
        cantidadProCompColumna.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        
        TableColumn<detalle_compra, Integer> costoProCompColumna = new TableColumn<>("Costo Compra");
        costoProCompColumna.setMinWidth(80);
        costoProCompColumna.setCellValueFactory(new PropertyValueFactory<>("costo_compra"));
              
        tvProductosSelecc.getColumns().addAll(codigoProCompColumna, descrProCompColumna, cantidadProCompColumna, costoProCompColumna);
        tvProductosSelecc.setItems(lstDetcompra);

        tvCompras.setOnMouseClicked((event) -> {
           if (tvCompras.getItems().size()>0){
             compra compT = new compra();
             compT = (compra) tvCompras.getSelectionModel().getSelectedItem();
             tfFolioNotaCompra.setText(compT.getFolio());
             tfCodigoFactura.setText(compT.getCodigo_factura());
             lstWhere.clear();
             lstWhere.add("id_compra = "+compT.getId_compra());
             lstDetcompra = FXCollections.observableArrayList(detCompDAO.consultaDetComp(lstWhere));
             for (detalle_compra detc: lstDetcompra){
                 lstWhere.add("codigo_prod = "+detc.getCodigo_prod());
                 detc.setDescripcion(invent.consultaInventario(lstWhere).get(0).getDescripcion());
             }
             tvProductosSelecc.setItems(lstDetcompra);
           }
        });

        Button btnAgregarProducto = new Button("Modificar Producto");
        btnAgregarProducto.setOnAction((ActionEvent e)->{
            
            detalle_compra det_Compra = new detalle_compra();
            det_Compra = (detalle_compra)tvProductosSelecc.getSelectionModel().getSelectedItem();

            lstWhere.clear();
            lstWhere.add("codigo_prod = "+tfCodigoProducto.getText());
            inventario invTemp = invent.consultaInventario(lstWhere).get(0);
            int exist = invTemp.getExistencia();
            exist = exist - det_Compra.getCantidad();
            invent.modificarExistenciaProducto(det_Compra.getCodigo_prod(), exist);
            exist = exist + Integer.parseInt(tfCantidad.getText());
            invent.modificarExistenciaProducto(det_Compra.getCodigo_prod(), exist);
            tvProductosSelecc.getItems().remove(
            tvProductosSelecc.getSelectionModel().getSelectedIndex());
            
            det_Compra.setCodigo_prod(Integer.parseInt(tfCodigoProducto.getText()));
            det_Compra.setDescripcion(tfDescrProd.getText());
            det_Compra.setCantidad(Integer.parseInt(tfCantidad.getText()));
            float fcosto = Float.valueOf(tfCostoCompra.getText()); 
            det_Compra.setCosto_compra(fcosto);
            lstDetcompra.add(det_Compra);
            tvProductosSelecc.setItems(lstDetcompra);
        });
        
        MenuItem miEliminaProSelec = new MenuItem("Eliminar");
        miEliminaProSelec.setOnAction((event) -> {
            detalle_compra detComTemp= (detalle_compra) tvProductosSelecc.getSelectionModel().getSelectedItem();
            lstWhere.clear();
            lstWhere.add("codigo_prod = "+tfCodigoProducto.getText());
            inventario invTemp = invent.consultaInventario(lstWhere).get(0);
            int exist = invTemp.getExistencia();
            int retExist = detComTemp.getCantidad();
            if (exist > retExist){
              exist = exist - detComTemp.getCantidad();
              detCompDAO.borrarDetComp(detComTemp.getId_detalle_compra());
              invent.modificarExistenciaProducto(detComTemp.getCodigo_prod(), exist);
              tvProductosSelecc.getItems().remove(
              tvProductosSelecc.getSelectionModel().getSelectedIndex());
            }else{
              Alert resp = new Alert(Alert.AlertType.ERROR);
              resp.setTitle("Error");
              resp.setContentText("No se puede retirar existencia por resta negativa en inventario!! ");
              resp.showAndWait();
            }
        });
        ContextMenu cmOpcionEliminar = new ContextMenu();
        cmOpcionEliminar.getItems().add(miEliminaProSelec);
        tvProductosSelecc.setContextMenu(cmOpcionEliminar);
        
        tvProductosSelecc.setOnMouseClicked((event) -> {
            detalle_compra detcompT = (detalle_compra) tvProductosSelecc.getSelectionModel().getSelectedItem();
            tfCantidad.setText(String.valueOf(detcompT.getCantidad()));
            tfCodigoProducto.setText(String.valueOf(detcompT.getCodigo_prod()));
            tfDescrProd.setText(detcompT.getDescripcion());
            tfCostoCompra.setText(String.valueOf(detcompT.getCosto_compra()));
        });
        
        Button btnGenerar = new Button("Generar Reporte ..");
        btnGenerar.setOnAction((event) -> {
         try{
            
           /* User home directory location */
            //String userHomeDirectory = System.getProperty("user.home");
            /* Output file location */
            
            File file;
            JasperReport jasperReport;
            file = new File("Reportes/Formatos/compras.jasper");
            jasperReport = (JasperReport) JRLoader.loadObject(file);
            LocalDateTime ld = LocalDateTime.now();
            String fechaFile = String.valueOf(ld.getDayOfMonth())+String.valueOf(ld.getMonth())+String.valueOf(ld.getYear())+String.valueOf(ld.getHour())+String.valueOf(ld.getMinute())+String.valueOf(ld.getSecond());
            //String outputFile = userHomeDirectory + File.separatorChar + "ReporteDiario"+fechaFile+".pdf";
            //String outputFile = userHomeDirectory + File.separatorChar + "ReporteDiario"+fechaFile+".pdf";
            String outputFile = "Reportes/Compras/" + File.separatorChar + "Compras"+fechaFile+".pdf";
           Map<String, Object> parameters = new HashMap<>();
           JRBeanCollectionDataSource itemsProductosComprados = new JRBeanCollectionDataSource(tvProductosSelecc.getItems());           
            List<String> listWhere = new ArrayList<>();
            listWhere.add("id_suc = 1");
            List<SUCURSAL> sucList = sucDAO.consultaSucursal(listWhere);
            SUCURSAL sucIdent = sucList.get(0);
           parameters.put("itemsProductosComprados", itemsProductosComprados);
           parameters.put("Factura", tfCodigoFactura.getText());
           parameters.put("Folio", tfFolioNotaCompra.getText());
           parameters.put("Sucursal", sucIdent.getNombre());
           parameters.put("Fecha", dpFecha.getValue().toString());
           
           /* Generando el PDF */
            //C:\Users\dopcan\Documents\NetBeansProjects\ClasesConsultorio\src\gestionconsultorio
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            /* outputStream to create PDF */
            OutputStream outputStream = new FileOutputStream(new File(outputFile));
            /* Write content to PDF file */
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            outputStream.close();
              Alert resp = new Alert(Alert.AlertType.INFORMATION);
              resp.setTitle("Informacion");
              resp.setContentText("Reporte Generado en Carpeta Reportes/Compras!! ");
              resp.showAndWait();
            
        } catch (JRException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
                 Logger.getLogger(CRMPLAMAR.class.getName()).log(Level.SEVERE, null, ex);
             }            
        });
        
        VBox vbHead = new VBox();

        HBox hbTipoSeleccion = new HBox();
        hbTipoSeleccion.getChildren().addAll(rbTodos, rbCodigoFactura, rbFolio, rbFecha);
        hbTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        hbTipoSeleccion.setSpacing(5);
        
        VBox vbCodigo = new VBox();
        vbCodigo.getChildren().addAll(lbCodigo, tfCodigo);
        vbCodigo.setSpacing(5);
        
        VBox vbFolioNota = new VBox();
        vbFolioNota.getChildren().addAll(lbFolioNota, tfFolio);
        vbFolioNota.setSpacing(5);
        
        VBox vbFechaSelec = new VBox();
        vbFechaSelec.getChildren().addAll(lbFecha, dpFechaCompra);
        vbFechaSelec.setSpacing(5);
        
        HBox hbCompSeleccion = new HBox();
        Button btnBuscarProductos = new Button("Seleccionar");
        btnBuscarProductos.setMaxHeight(50);
        btnBuscarProductos.setOnAction((ActionEvent e)->{
         List<inventario> lstInv = new ArrayList<>();
         List<String> lstWherelc = new ArrayList<>();
         
          if (rbTodos.isSelected()){
           lstWhere.clear();
           lstWhere.add("id_compra is not null");
           lstCompra = FXCollections.observableArrayList(compDAO.consultaCompra(lstWhere));
           tvCompras.setItems(lstCompra);
         } 
         
         if (rbCodigoFactura.isSelected()){
           lstWhere.clear();
           lstWhere.add("codigo_factura = "+tfCodigo.getText());
           lstCompra = FXCollections.observableArrayList(compDAO.consultaCompra(lstWhere));
           tvCompras.setItems(lstCompra);
         }
         if (rbFolio.isSelected()){
           lstWhere.clear();
           lstWhere.add("folio = "+tfCodigo.getText());
           lstCompra = FXCollections.observableArrayList(compDAO.consultaCompra(lstWhere));
           tvCompras.setItems(lstCompra);
         }
         
         if (rbFecha.isSelected()){
           lstWhere.clear();
           lstWhere.add("fecha = '"+dpFechaCompra.getValue().toString()+"' ");
           lstCompra = FXCollections.observableArrayList(compDAO.consultaCompra(lstWhere));
           tvCompras.setItems(lstCompra);
         }
         
        });
        
        hbCompSeleccion.getChildren().addAll(vbCodigo, vbFolioNota, vbFechaSelec, btnBuscarProductos);
        hbCompSeleccion.setPadding(new Insets(10, 10, 10, 10));
        hbCompSeleccion.setSpacing(5);

        Separator spSeleccionProductos = new Separator();
        vbHead.getChildren().addAll(lbTipoBusqueda, hbTipoSeleccion, hbCompSeleccion, spSeleccionProductos);
        
        VBox vbTabCompra = new VBox();
        vbTabCompra.setPadding(new Insets(5, 5, 5, 5));
        
        vbTabCompra.getChildren().addAll(lbCompras, tvCompras, lbProducto, tvProductosSelecc);
        
        GridPane gpBloqueProducto = new GridPane();
        gpBloqueProducto.setPadding(new Insets(5, 5, 5, 5));
        gpBloqueProducto.setVgap(10);
        gpBloqueProducto.setHgap(10);
        
        gpBloqueProducto.add(lbFechaCompra , 0, 0);
        gpBloqueProducto.add(dpFecha , 1, 0);
        
        gpBloqueProducto.add(lbFolioNotaCompra , 2, 0);
        gpBloqueProducto.add(tfFolioNotaCompra , 3, 0);
        
        gpBloqueProducto.add(lbCodigoProd , 0, 1);
        gpBloqueProducto.add(tfCodigoProducto , 1, 1);
        
        gpBloqueProducto.add(lbDescProducto , 2, 1);
        gpBloqueProducto.add(tfDescrProd , 3, 1);
        
        gpBloqueProducto.add(lbCantidad , 0, 2);
        gpBloqueProducto.add(tfCantidad , 1, 2);
        
        gpBloqueProducto.add(lbCodigoFactura , 2, 2);
        gpBloqueProducto.add(tfCodigoFactura , 3, 2);
        
        gpBloqueProducto.add(lbCostoCompra , 0, 3);
        gpBloqueProducto.add(tfCostoCompra , 1, 3);
        
        gpBloqueProducto.add(btnAgregarProducto, 3, 3);

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        
        Button btnGuardar = new Button("Modificar Compra");
        btnGuardar.setOnAction((event) -> {
              int idCompReg=0;
              
              compraDAO compDAO = new compraDAO();
              detalle_compraDAO detcompDAO = new detalle_compraDAO();

              compra comp = new compra();
              comp = (compra) tvCompras.getSelectionModel().getSelectedItem();
              comp.setCodigo_factura(tfCodigoFactura.getText());
              comp.setFecha(dpFecha.getValue().toString());
              comp.setFolio(tfFolioNotaCompra.getText());
              compDAO.modificarCompra(comp);
                for (int i =0; i<lstDetcompra.size(); i++){
                  detalle_compra detCom = lstDetcompra.get(i);
                  //detCom.setId_compra(idCompReg);
                  detcompDAO.modificarDetComp(detCom);
                  /*lstWhere.add("codigo_prod= "+detCom.getCodigo_prod());
                  List<inventario> lstiv = invent.consultaInventario(lstWhere);
                  inventario iv = lstiv.get(0);
                  int existenciaProd = iv.getExistencia();
                  int nuevaExistenciaProd = existenciaProd + detCom.getCantidad();
                  invent.modificarExistenciaProducto(detCom.getCodigo_prod(), nuevaExistenciaProd);*/             
                }
                Alert altMensaje = new Alert(Alert.AlertType.INFORMATION);
                altMensaje.setContentText("Compra de Productos Registrada");
                altMensaje.setTitle("Informacion-Compra");
                altMensaje.show(); 
                lstDetcompra.clear();
                tfCantidad.setText("");
                tfCodigo.setText("");
                tfCodigoFactura.setText("");
                tfCodigoProducto.setText("");
                tfCostoCompra.setText("");
                tfDescrProd.setText("");
                tfFolioNotaCompra.setText("");
        });
        
        HBox hbBotonesInferiores = new HBox();
        hbBotonesInferiores.setAlignment(Pos.CENTER_RIGHT);
        hbBotonesInferiores.getChildren().addAll(btnCancelar, btnGuardar, btnGenerar);
        
        VBox vbTabProducto = new VBox();
        vbTabProducto.setSpacing(5);
        vbTabProducto.getChildren().addAll(gpBloqueProducto, hbBotonesInferiores);
        
        HBox hbBody = new HBox();
        hbBody.getChildren().addAll(vbTabCompra, vbTabProducto);
        
        vbVistaPpal.getChildren().addAll(lbTituloVista, vbHead,hbBody);
        
        return vbVistaPpal; 
    }
    private VBox vistaEliminarConsultarCompra(){
        Label lbTituloVista = new Label("ELIMINAR COMPRAS");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        if (lstCompra.size()>0){
            //lstCompra = FXCollections.observableArrayList();
            lstCompra.clear();
        }
        if (!lstDetcompra.isEmpty()){lstDetcompra.clear();}
        
        VBox vbVistaPpal = new VBox();
        
        Label lbFechaCompra = new Label("Fecha Compra: ");
        Label lbFolioNotaCompra = new Label("Folio Nota Compra: ");
	Label lbCantidad  = new Label("Cantidad: ");
	Label lbCodigoProd  = new Label("Codigo Producto: ");
        Label lbDescProducto = new Label ("Descripción Producto: ");
        Label lbCostoCompra = new Label ("Costo Compra Unitario: ");
        Label lbCodigoFactura = new Label ("Codigo Factura: ");
        
        
        DatePicker dpFecha = new DatePicker(LocalDate.now());
        TextField tfFolioNotaCompra = new TextField();
        TextField tfCostoCompra = new TextField();
        tfCostoCompra.setMaxWidth(80);
        TextField tfCantidad = new TextField();
        tfCantidad.setMaxWidth(80);
        TextField tfCodigoFactura = new TextField();
        TextField tfCodigoProducto = new TextField();
        TextField tfDescrProd = new TextField();
        tfDescrProd.setMaxWidth(300);
        tfDescrProd.setPrefWidth(300);
        
        //Componentes de Interfaz
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgBusquedas = new ToggleGroup();
        
        RadioButton rbTodos = new RadioButton("Todos");
        RadioButton rbCodigoFactura = new RadioButton("Codigo Factura");
        RadioButton rbFolio = new RadioButton("Folio Nota Remision");
        RadioButton rbFecha = new RadioButton("Fecha Compra");
        rbFecha.setSelected(true);
        
        rbTodos.setToggleGroup(tgBusquedas);
        rbCodigoFactura.setToggleGroup(tgBusquedas);
        rbFolio.setToggleGroup(tgBusquedas);
        rbFecha.setToggleGroup(tgBusquedas);
               
        Label lbCodigo = new Label("Codigo Factura: ");
        TextField tfCodigo = new TextField();
        Label lbFolioNota = new Label("Folio Nota Remision: ");
        TextField tfFolio = new TextField();
        Label lbFecha = new Label("Fecha Compra:");
        DatePicker dpFechaCompra = new DatePicker(LocalDate.now());
        
        
        Label lbCompras = new Label("Tabla Compras: ");
        TableView tvCompras = new TableView();
        tvCompras.setPrefHeight(350);
        tvCompras.setPrefWidth(550);
        
        TableColumn<compra, Integer> idCompraColumna = new TableColumn<>("Id");
        idCompraColumna.setMinWidth(120);
        idCompraColumna.setCellValueFactory(new PropertyValueFactory<>("id_compra"));

        TableColumn<compra, String> fechaColumna = new TableColumn<>("Fecha");
        fechaColumna.setMinWidth(120);
        fechaColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        
        TableColumn<compra, String> codFacColumna = new TableColumn<>("Codigo Factura");
        codFacColumna.setMinWidth(120);
        codFacColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_factura"));
        
        TableColumn<compra, String> folioRemisionColumna = new TableColumn<>("Folio Remision");
        folioRemisionColumna.setMinWidth(120);
        folioRemisionColumna.setCellValueFactory(new PropertyValueFactory<>("folio"));        

        tvCompras.getColumns().addAll(idCompraColumna, fechaColumna, codFacColumna,
                folioRemisionColumna);
        
        LocalDate ldToday = dpFechaCompra.getValue();

        List<String> lstWhere = new ArrayList<>();
        lstWhere.clear();
        lstWhere.add("fecha = '"+ldToday.toString()+"' ");
        //lstWhere.add("id_compra is not null");
        lstCompra = FXCollections.observableArrayList(compDAO.consultaCompra(lstWhere));
        tvCompras.setItems(lstCompra);
        
        Label lbProducto = new Label("Tabla Producto Comprados: ");
        TableView tvProductosSelecc = new TableView();
        tvProductosSelecc.setPrefHeight(350);
        tvProductosSelecc.setPrefWidth(550);
        
        TableColumn<detalle_compra, Integer> codigoProCompColumna = new TableColumn<>("Codigo Producto");
        codigoProCompColumna.setMinWidth(80);
        codigoProCompColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));
        
        TableColumn<detalle_compra, String> descrProCompColumna = new TableColumn<>("Descripción Producto");
        descrProCompColumna.setMinWidth(220);
        descrProCompColumna.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        
        TableColumn<detalle_compra, Integer> cantidadProCompColumna = new TableColumn<>("Cantidad");
        cantidadProCompColumna.setMinWidth(80);
        cantidadProCompColumna.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        
        TableColumn<detalle_compra, Integer> costoProCompColumna = new TableColumn<>("Costo Compra");
        costoProCompColumna.setMinWidth(80);
        costoProCompColumna.setCellValueFactory(new PropertyValueFactory<>("costo_compra"));
              
        tvProductosSelecc.getColumns().addAll(codigoProCompColumna, descrProCompColumna, cantidadProCompColumna, costoProCompColumna);
        tvProductosSelecc.setItems(lstDetcompra);

        tvCompras.setOnMouseClicked((event) -> {
           if (tvCompras.getItems().size()>0){
             compra compT = new compra();
             compT = (compra) tvCompras.getSelectionModel().getSelectedItem();
             tfFolioNotaCompra.setText(compT.getFolio());
             tfCodigoFactura.setText(compT.getCodigo_factura());
             lstWhere.clear();
             lstWhere.add("id_compra = "+compT.getId_compra());
             lstDetcompra = FXCollections.observableArrayList(detCompDAO.consultaDetComp(lstWhere));
             for (detalle_compra detc: lstDetcompra){
                 lstWhere.add("codigo_prod = "+detc.getCodigo_prod());
                 detc.setDescripcion(invent.consultaInventario(lstWhere).get(0).getDescripcion());
             }
             tvProductosSelecc.setItems(lstDetcompra);
           }
        });

       
        tvProductosSelecc.setOnMouseClicked((event) -> {
            detalle_compra detcompT = (detalle_compra) tvProductosSelecc.getSelectionModel().getSelectedItem();
            tfCantidad.setText(String.valueOf(detcompT.getCantidad()));
            tfCodigoProducto.setText(String.valueOf(detcompT.getCodigo_prod()));
            tfDescrProd.setText(detcompT.getDescripcion());
            tfCostoCompra.setText(String.valueOf(detcompT.getCosto_compra()));
        });
        
        VBox vbHead = new VBox();

        HBox hbTipoSeleccion = new HBox();
        hbTipoSeleccion.getChildren().addAll(rbTodos, rbCodigoFactura, rbFolio, rbFecha);
        hbTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        hbTipoSeleccion.setSpacing(5);
        
        VBox vbCodigo = new VBox();
        vbCodigo.getChildren().addAll(lbCodigo, tfCodigo);
        vbCodigo.setSpacing(5);
        
        VBox vbFolioNota = new VBox();
        vbFolioNota.getChildren().addAll(lbFolioNota, tfFolio);
        vbFolioNota.setSpacing(5);
        
        VBox vbFechaSelec = new VBox();
        vbFechaSelec.getChildren().addAll(lbFecha, dpFechaCompra);
        vbFechaSelec.setSpacing(5);
        
        HBox hbCompSeleccion = new HBox();
        Button btnBuscarProductos = new Button("Seleccionar");
        btnBuscarProductos.setMaxHeight(50);
        btnBuscarProductos.setOnAction((ActionEvent e)->{
         List<inventario> lstInv = new ArrayList<>();
         List<String> lstWherelc = new ArrayList<>();
         
          if (rbTodos.isSelected()){
           lstWhere.clear();
           lstWhere.add("id_compra is not null");
           lstCompra = FXCollections.observableArrayList(compDAO.consultaCompra(lstWhere));
           tvCompras.setItems(lstCompra);
         } 
         
         if (rbCodigoFactura.isSelected()){
           lstWhere.clear();
           lstWhere.add("codigo_factura = "+tfCodigo.getText());
           lstCompra = FXCollections.observableArrayList(compDAO.consultaCompra(lstWhere));
           tvCompras.setItems(lstCompra);
         }
         if (rbFolio.isSelected()){
           lstWhere.clear();
           lstWhere.add("folio = "+tfCodigo.getText());
           lstCompra = FXCollections.observableArrayList(compDAO.consultaCompra(lstWhere));
           tvCompras.setItems(lstCompra);
         }
         
         if (rbFecha.isSelected()){
           lstWhere.clear();
           lstWhere.add("fecha = '"+dpFechaCompra.getValue().toString()+"' ");
           lstCompra = FXCollections.observableArrayList(compDAO.consultaCompra(lstWhere));
           tvCompras.setItems(lstCompra);
         }
         
        });
        
        hbCompSeleccion.getChildren().addAll(vbCodigo, vbFolioNota, vbFechaSelec, btnBuscarProductos);
        hbCompSeleccion.setPadding(new Insets(10, 10, 10, 10));
        hbCompSeleccion.setSpacing(5);

        Separator spSeleccionProductos = new Separator();
        vbHead.getChildren().addAll(lbTipoBusqueda, hbTipoSeleccion, hbCompSeleccion, spSeleccionProductos);
        
        VBox vbTabCompra = new VBox();
        vbTabCompra.setPadding(new Insets(5, 5, 5, 5));
        
        vbTabCompra.getChildren().addAll(lbCompras, tvCompras, lbProducto, tvProductosSelecc);
        
        GridPane gpBloqueProducto = new GridPane();
        gpBloqueProducto.setPadding(new Insets(5, 5, 5, 5));
        gpBloqueProducto.setVgap(10);
        gpBloqueProducto.setHgap(10);
        
        gpBloqueProducto.add(lbFechaCompra , 0, 0);
        gpBloqueProducto.add(dpFecha , 1, 0);
        
        gpBloqueProducto.add(lbFolioNotaCompra , 2, 0);
        gpBloqueProducto.add(tfFolioNotaCompra , 3, 0);
        
        gpBloqueProducto.add(lbCodigoProd , 0, 1);
        gpBloqueProducto.add(tfCodigoProducto , 1, 1);
        
        gpBloqueProducto.add(lbDescProducto , 2, 1);
        gpBloqueProducto.add(tfDescrProd , 3, 1);
        
        gpBloqueProducto.add(lbCantidad , 0, 2);
        gpBloqueProducto.add(tfCantidad , 1, 2);
        
        gpBloqueProducto.add(lbCodigoFactura , 2, 2);
        gpBloqueProducto.add(tfCodigoFactura , 3, 2);
        
        gpBloqueProducto.add(lbCostoCompra , 0, 3);
        gpBloqueProducto.add(tfCostoCompra , 1, 3);
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        
        Button btnEliminar = new Button("Eliminar Compra");
        btnEliminar.setOnAction((event) -> {
              //compraDAO compDAO = new compraDAO();
            
              for(detalle_compra i:lstDetcompra){
                  lstWhere.clear();
                  lstWhere.add("codigo_prod = "+i.getCodigo_prod());
                  inventario invTemp = invent.consultaInventario(lstWhere).get(0);
                  int exist = invTemp.getExistencia();
                  exist = exist - i.getCantidad();
                  invent.modificarExistenciaProducto(i.getCodigo_prod(), exist);
                  detCompDAO.borrarDetComp(i.getId_detalle_compra());
              }
              compra compTemp;
              compTemp = (compra)tvCompras.getSelectionModel().getSelectedItem();
              System.out.println(compTemp.getId_compra());
              compDAO.borrarCompra(compTemp.getId_compra());
             
                Alert altMensaje = new Alert(Alert.AlertType.INFORMATION);
                altMensaje.setContentText("Compra de Productos Eliminada");
                altMensaje.setTitle("Informacion-Compra");
                altMensaje.showAndWait(); 
                //lstDetcompra.clear();
                tfCantidad.setText("");
                tfCodigo.setText("");
                tfCodigoFactura.setText("");
                tfCodigoProducto.setText("");
                tfCostoCompra.setText("");
                tfDescrProd.setText("");
                tfFolioNotaCompra.setText("");
                tvProductosSelecc.getItems().clear();
                tvCompras.getItems().clear();
                
        });

        HBox hbBotonesInferiores = new HBox();
        hbBotonesInferiores.setAlignment(Pos.CENTER_RIGHT);
        hbBotonesInferiores.getChildren().addAll(btnCancelar, btnEliminar);
        
        VBox vbTabProducto = new VBox();
        vbTabProducto.setSpacing(5);
        vbTabProducto.getChildren().addAll(gpBloqueProducto, hbBotonesInferiores);
        
        HBox hbBody = new HBox();
        hbBody.getChildren().addAll(vbTabCompra, vbTabProducto);
        
        vbVistaPpal.getChildren().addAll(lbTituloVista, vbHead,hbBody);
        
        return vbVistaPpal; 
    }
    private VBox vistaNuevaCategoria(){
        
        categoriaDAO catDAO = new categoriaDAO();
        
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("AGREGAR CATEGORIA");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        Label lbNombreCategoria = new Label("Nombre Categoria");
        Label lbIdParentCategoria = new Label("Id Padre Categoria");
        
        TextField tfNombreCategoria = new TextField();
        TextField tfIdParentCategoria = new TextField();
        
        TableView tvCategorias = new TableView();
        tvCategorias.setPrefHeight(320);
        tvCategorias.setMaxWidth(480);
                
        TableColumn<categoria, Integer> idCategoriaColumna = new TableColumn<>("Id Categoria");
        idCategoriaColumna.setMinWidth(120);
        idCategoriaColumna.setCellValueFactory(new PropertyValueFactory<>("id_categoria"));
        
        TableColumn<categoria, Integer> nombreCategoriaColumna = new TableColumn<>("Nombre Categoria");
        nombreCategoriaColumna.setMinWidth(220);
        nombreCategoriaColumna.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        
        TableColumn<categoria, Integer> parentIdCategoriaColumna = new TableColumn<>("Parent Id Categoria");
        parentIdCategoriaColumna.setMinWidth(120);
        parentIdCategoriaColumna.setCellValueFactory(new PropertyValueFactory<>("parent_id"));
        
        tvCategorias.getColumns().addAll(idCategoriaColumna, nombreCategoriaColumna, parentIdCategoriaColumna);
        
         List<inventario> lstInv = new ArrayList<>();
         List<String> lstWhere = new ArrayList<>();
         lstWhere.add("id_categoria is not null");
        tvCategorias.setItems(catDAO.consultarCategoria(lstWhere));
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction((ActionEvent e)->{
            removerVistas();
        });
        
        
        Button btnGuardar = new Button("Agregar");
        btnGuardar.setOnAction((ActionEvent e)->{
            categoria catIdent = new categoria();
            catIdent.setCategoria(tfNombreCategoria.getText().toUpperCase());
            catIdent.setParent_id(Integer.parseInt(tfIdParentCategoria.getText()));
            catDAO.insertarCategoria(catIdent);
            removerVistas();
        });
        
        HBox hbBotones = new HBox();
        hbBotones.setPadding(new Insets(5, 5, 5, 5));
        hbBotones.setSpacing(5);
        hbBotones.getChildren().addAll(btnCancelar, btnGuardar);
        
        GridPane gpDatosCAegoria = new GridPane();
        gpDatosCAegoria.setPadding(new Insets(5, 5, 5, 5));
        gpDatosCAegoria.setVgap(5);
        gpDatosCAegoria.setHgap(5);
        
        gpDatosCAegoria.add(lbNombreCategoria, 0, 1);
        gpDatosCAegoria.add(tfNombreCategoria, 1, 1);
        gpDatosCAegoria.add(lbIdParentCategoria,0,2);
        gpDatosCAegoria.add(tfIdParentCategoria,1,2);
        gpDatosCAegoria.add(hbBotones,1,3);
        gpDatosCAegoria.setAlignment(Pos.CENTER);
        
        vbPpal.getChildren().addAll(lbTituloVista, gpDatosCAegoria, tvCategorias);
        
        return vbPpal;    
    }
    private VBox vistaModificarCategoria(){
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("MODIFICAR CATEGORIA");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        List<categoria> lstCategoria = new ArrayList();
        List<String> lstWhere = new ArrayList();
        lstWhere.add("id_categoria is not null");
        obList.setAll(categDAO.consultarCategoria(lstWhere));
        TableView tvCategorias = new TableView();

        TableColumn idCategoriaColumna = new TableColumn("Id Categoria");
        idCategoriaColumna.setMinWidth(120);
        idCategoriaColumna.setCellValueFactory(new PropertyValueFactory<>("id_categoria"));
        
        TableColumn categoriaColumna = new TableColumn("Categoria");
        categoriaColumna.setMinWidth(320);
        categoriaColumna.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        TableColumn parenIdColumna = new TableColumn("Id Categoria Padre");
        parenIdColumna.setMinWidth(220);
        parenIdColumna.setCellValueFactory(new PropertyValueFactory<>("parent_id"));
       
        tvCategorias.getColumns().addAll(idCategoriaColumna, categoriaColumna, parenIdColumna);
        tvCategorias.setItems(obList);
        
         Label lbIdCAtegoria = new Label("Id Categoria: ");
         Label lbCategoria = new Label("Categoria : ");
         Label lbIdCategoriaPadre = new Label("Id Categoria Padre: ");
         
         TextField tfIdCategoria = new TextField();
           tfIdCategoria.setPrefWidth(80);
           tfIdCategoria.setMaxWidth(80);
           tfIdCategoria.setEditable(false);
         TextField tfCategoria = new TextField();
           tfCategoria.setPrefWidth(200);
         TextField tfIdCategoriaPadre = new TextField();
           tfIdCategoriaPadre.setMaxWidth(80);
         
         Button btnCancelar = new Button("Cancelar");
         btnCancelar.setOnAction((ActionEvent e)->{
             if(vbAreaTrabajo.getChildren().size()>=0){
                 vbAreaTrabajo.getChildren().remove(0);
             }
         });
         
         Button btnModificar = new Button("Modificar");
         btnModificar.setOnAction((ActionEvent e)->{
           catIdent.setId_categoria(Integer.parseInt(tfIdCategoria.getText()));
           catIdent.setCategoria(tfCategoria.getText().toUpperCase());
           catIdent.setParent_id(Integer.parseInt(tfIdCategoriaPadre.getText()));
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setHeaderText(null);
           alert.setTitle("Confirmación");
           alert.setContentText("¿Estas seguro de confirmar la acción?");
           Optional<ButtonType> action = alert.showAndWait(); 
           if (action.get() == ButtonType.OK) {
              categDAO.modificarCategoria(catIdent);
              removerVistas();
           } else {
              removerVistas();
           }            
            
         });
         
        tvCategorias.setOnMouseClicked((event) -> {
          //tfNombreProveedor.setText("Seleccionaste: "+ tvProveedores.getSelectionModel().getSelectedIndex());
          categoria catIde = (categoria)tvCategorias.getSelectionModel().getSelectedItem();
          tfIdCategoria.setText(String.valueOf(catIde.getId_categoria()));
          tfCategoria.setText(catIde.getCategoria());
          tfIdCategoriaPadre.setText(String.valueOf(catIde.getParent_id()));
         
        });
         
         HBox hbBotones = new HBox(btnCancelar, btnModificar);
         hbBotones.setSpacing(10);
        
         GridPane gpPrincipal = new GridPane();
         gpPrincipal.setPadding(new Insets(5, 5, 5, 5));
         gpPrincipal.setHgap(10);
         gpPrincipal.setVgap(10);
         
         gpPrincipal.add(lbIdCAtegoria, 0, 0);
         gpPrincipal.add(tfIdCategoria, 1, 0);
         gpPrincipal.add(lbCategoria, 0, 1);
         gpPrincipal.add(tfCategoria, 1, 1);
         gpPrincipal.add(lbIdCategoriaPadre, 0, 2);
         gpPrincipal.add(tfIdCategoriaPadre, 1, 2);
         gpPrincipal.add(hbBotones, 1, 3);
        
         vbPpal.getChildren().addAll(lbTituloVista, tvCategorias, gpPrincipal);
        return vbPpal; 

    }
    private VBox vistaEliminarCategoria(){
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("ELIMINAR CATEGORIA");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        List<categoria> lstCategoria = new ArrayList();
        List<String> lstWhere = new ArrayList();
        lstWhere.add("id_categoria is not null");
        obList.setAll(categDAO.consultarCategoria(lstWhere));
        TableView tvCategorias = new TableView();

        TableColumn idCategoriaColumna = new TableColumn("Id Categoria");
        idCategoriaColumna.setMinWidth(120);
        idCategoriaColumna.setCellValueFactory(new PropertyValueFactory<>("id_categoria"));
        
        TableColumn categoriaColumna = new TableColumn("Categoria");
        categoriaColumna.setMinWidth(320);
        categoriaColumna.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        TableColumn parenIdColumna = new TableColumn("Id Categoria Padre");
        parenIdColumna.setMinWidth(220);
        parenIdColumna.setCellValueFactory(new PropertyValueFactory<>("parent_id"));
       
        tvCategorias.getColumns().addAll(idCategoriaColumna, categoriaColumna, parenIdColumna);
        tvCategorias.setItems(obList);
        
         Label lbIdCAtegoria = new Label("Id Categoria: ");
         Label lbCategoria = new Label("Categoria : ");
         Label lbIdCategoriaPadre = new Label("Id Categoria Padre: ");
         
         TextField tfIdCategoria = new TextField();
           tfIdCategoria.setPrefWidth(80);
           tfIdCategoria.setMaxWidth(80);
           tfIdCategoria.setEditable(false);
         TextField tfCategoria = new TextField();
           tfCategoria.setPrefWidth(200);
           tfCategoria.setEditable(false);
         TextField tfIdCategoriaPadre = new TextField();
           tfIdCategoriaPadre.setMaxWidth(80);
           tfIdCategoriaPadre.setEditable(false);
         
         Button btnCancelar = new Button("Cancelar");
         btnCancelar.setOnAction((ActionEvent e)->{
             if(vbAreaTrabajo.getChildren().size()>=0){
                 vbAreaTrabajo.getChildren().remove(0);
             }
         });
         
         Button btnModificar = new Button("Eliminar");
         btnModificar.setOnAction((ActionEvent e)->{
           catIdent.setId_categoria(Integer.parseInt(tfIdCategoria.getText()));
           catIdent.setCategoria(tfCategoria.getText().toUpperCase());
           catIdent.setParent_id(Integer.parseInt(tfIdCategoriaPadre.getText()));
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setHeaderText(null);
           alert.setTitle("Confirmación");
           alert.setContentText("¿Estas seguro de confirmar la acción?");
           Optional<ButtonType> action = alert.showAndWait(); 
           if (action.get() == ButtonType.OK) {
              categDAO.borrarCategoria(catIdent.getId_categoria());
              removerVistas();
           } else {
              removerVistas();
           }            
            
         });
         
        tvCategorias.setOnMouseClicked((event) -> {
          //tfNombreProveedor.setText("Seleccionaste: "+ tvProveedores.getSelectionModel().getSelectedIndex());
          categoria catIde = (categoria)tvCategorias.getSelectionModel().getSelectedItem();
          tfIdCategoria.setText(String.valueOf(catIde.getId_categoria()));
          tfCategoria.setText(catIde.getCategoria());
          tfIdCategoriaPadre.setText(String.valueOf(catIde.getParent_id()));
         
        });
         
         HBox hbBotones = new HBox(btnCancelar, btnModificar);
         hbBotones.setSpacing(10);
        
         GridPane gpPrincipal = new GridPane();
         gpPrincipal.setPadding(new Insets(5, 5, 5, 5));
         gpPrincipal.setHgap(10);
         gpPrincipal.setVgap(10);
         
         gpPrincipal.add(lbIdCAtegoria, 0, 0);
         gpPrincipal.add(tfIdCategoria, 1, 0);
         gpPrincipal.add(lbCategoria, 0, 1);
         gpPrincipal.add(tfCategoria, 1, 1);
         gpPrincipal.add(lbIdCategoriaPadre, 0, 2);
         gpPrincipal.add(tfIdCategoriaPadre, 1, 2);
         gpPrincipal.add(hbBotones, 1, 3);
        
         vbPpal.getChildren().addAll(lbTituloVista, tvCategorias, gpPrincipal);
        return vbPpal;

    }
    private void ventanaSeleccionarClientes(){
       Stage stgPpal = new Stage();
       TableView tvClientes = new TableView();
       
        TableColumn<CLIENTE, String> codClientColumna = new TableColumn<>("Codigo Cliente");
        codClientColumna.setMinWidth(120);
        codClientColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_cliente"));
        
        TableColumn<CLIENTE, String> nombreColumna = new TableColumn<>("Nombre");
        nombreColumna.setMinWidth(320);
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        
        TableColumn<CLIENTE, String> razonSocialColumna = new TableColumn<>("Razon Social");
        razonSocialColumna.setMinWidth(320);
        razonSocialColumna.setCellValueFactory(new PropertyValueFactory<>("razon_social"));
        
        TableColumn<CLIENTE, String> domFiscalColumna = new TableColumn<>("Domicilio Fiscal");
        domFiscalColumna.setMinWidth(320);
        domFiscalColumna.setCellValueFactory(new PropertyValueFactory<>("domicilio_fiscal"));
        
        TableColumn<CLIENTE, String> telefonoColumna = new TableColumn<>("Telefono");
        telefonoColumna.setMinWidth(120);
        telefonoColumna.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        
        TableColumn<CLIENTE, String> rfcColumna = new TableColumn<>("RFC");
        rfcColumna.setMinWidth(120);
        rfcColumna.setCellValueFactory(new PropertyValueFactory<>("rfc"));
        
        TableColumn<CLIENTE, String> emailColumna = new TableColumn<>("E-Mail");
        emailColumna.setMinWidth(120);
        emailColumna.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        tvClientes.getColumns().addAll(codClientColumna, nombreColumna, razonSocialColumna, domFiscalColumna,
                telefonoColumna, rfcColumna, emailColumna);
        
         List<CLIENTE> lstInv = new ArrayList<>();
         List<String> lstWhere = new ArrayList<>();
         lstWhere.add("codigo_cliente is not null");
         lstInv=cliDAO.consultarClientes(lstWhere);
         ObservableList obList = FXCollections.observableArrayList(lstInv);
         tvClientes.setItems(obList);
         
       stgPpal.setTitle("Seleccion del Cliente");
       
       VBox vbPpal = new VBox();
       vbPpal.setSpacing(5);
       
       Button btnCancelar = new Button("Cancelar");
       btnCancelar.setOnAction((ActionEvent e)->{
           stgPpal.close();
       });
       Button btnSeleccionar = new Button("Seleccionar");
       btnSeleccionar.setOnAction((ActionEvent e)->{
           clIdent =(CLIENTE) tvClientes.getSelectionModel().getSelectedItem();
           this.tfCodigoCliente.setText(String.valueOf(clIdent.getCodigo_cliente()));
           this.tfNombre.setText(clIdent.getNombre());
           this.tfDomicilioFiscal.setText(clIdent.getDomicilio_fiscal());
           this.tfEmail.setText(clIdent.getEmail());
           this.tfRFC.setText(clIdent.getRfc());
           this.tfRazonSocial.setText(clIdent.getRazon_social());
           this.tfTelefono.setText(clIdent.getTelefono());
           stgPpal.close();
       });
       
       tvClientes.setOnKeyPressed((event) -> {
           if (event.getCode()== KeyCode.ENTER){
               clIdent =(CLIENTE) tvClientes.getSelectionModel().getSelectedItem();
               this.tfCodigoCliente.setText(String.valueOf(clIdent.getCodigo_cliente()));
               this.tfNombre.setText(clIdent.getNombre());
               this.tfDomicilioFiscal.setText(clIdent.getDomicilio_fiscal());
               this.tfEmail.setText(clIdent.getEmail());
               this.tfRFC.setText(clIdent.getRfc());
               this.tfRazonSocial.setText(clIdent.getRazon_social());
               this.tfTelefono.setText(clIdent.getTelefono());
               stgPpal.close();
           }
       });
       HBox hbBotones = new HBox();
       hbBotones.setSpacing(5);
       hbBotones.getChildren().addAll(btnCancelar, btnSeleccionar);
       
       vbPpal.getChildren().addAll(tvClientes,hbBotones);
       
       StackPane rootSelectClientes = new StackPane();
       rootSelectClientes.getChildren().addAll(vbPpal);
       
       Scene scene = new Scene(rootSelectClientes,320,470);
       stgPpal.setScene(scene);
       stgPpal.initModality(Modality.WINDOW_MODAL);
       stgPpal.show();
    }
    private void ventanaVerProductosComprados(int codigoNotaVenta){
       Stage stgPpal = new Stage();
       TableView tvProductos = new TableView();
       
        TableColumn<detalle_venta, Integer> codproColumna = new TableColumn<>("Codigo");
        codproColumna.setMinWidth(90);
        codproColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));
        
        TableColumn<detalle_venta, String> descripColumna = new TableColumn<>("Descripcion");
        descripColumna.setMinWidth(320);
        descripColumna.setCellValueFactory(new PropertyValueFactory<>("descrprod"));
        
        TableColumn<detalle_venta, Integer> cantidadColumna = new TableColumn<>("Cantidad");
        cantidadColumna.setMinWidth(100);
        cantidadColumna.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        
        TableColumn<detalle_venta, String> costoUnitColumna = new TableColumn<>("Costo Unitario");
        costoUnitColumna.setMinWidth(100);
        costoUnitColumna.setCellValueFactory(new PropertyValueFactory<>("precio_venta"));
        
        TableColumn<detalle_venta, String> subtotalColumna = new TableColumn<>("Sub-Total");
        subtotalColumna.setMinWidth(100);
        subtotalColumna.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
        
        tvProductos.getColumns().addAll(codproColumna, descripColumna, cantidadColumna, costoUnitColumna,
                subtotalColumna);
        
         List<String> lstWhere = new ArrayList<>();
         lstWhere.clear();
         lstWhere.add("codigo_nota_venta = "+codigoNotaVenta);
         detventa.clear();
         detventa = FXCollections.observableArrayList(detventaDAO.consultaDetVenta(lstWhere));
         for (detalle_venta detVentaT: detventa){
             detVentaT.setSubTotal(detVentaT.getCantidad()*detVentaT.getPrecio_venta());
         }
         tvProductos.setItems(detventa);
         
       stgPpal.setTitle("Productos Comprados");
       
       VBox vbPpal = new VBox();
       vbPpal.setSpacing(5);
       
       Button btnCancelar = new Button("Salir");
       btnCancelar.setOnAction((ActionEvent e)->{
           stgPpal.close();
       });
       HBox hbBotones = new HBox();
       hbBotones.setSpacing(5);
       hbBotones.setAlignment(Pos.CENTER_RIGHT);
       hbBotones.getChildren().addAll(btnCancelar);
       
       vbPpal.getChildren().addAll(tvProductos, hbBotones);
       
       StackPane rootSelectClientes = new StackPane();
       rootSelectClientes.getChildren().addAll(vbPpal);
       
       Scene scene = new Scene(rootSelectClientes,720,470);
       stgPpal.setScene(scene);
       stgPpal.initModality(Modality.WINDOW_MODAL);
       stgPpal.show();
    }
    private VBox ventanaAgregarGastos(){
        VBox vbPpal = new VBox();
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("GREGAR GASTO");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);

        lstWhereConcepto.add("id_catalogo_gastos is not null");
        for(catalogoGasto lstCatGast: catGastoDAO.consultarCatGastos(lstWhereConcepto)){
               lstConceptosGastos.add(lstCatGast.getConcepto());
        }
        
        //id_gasto
        //concepto
	//fecha
	//monto
	//flag
        
        Label lbConceptoGasto = new Label("Concepto: ");
        Label lbFechaGasto = new Label("Fecha: ");
        Label lbMontoGasto = new Label("Monto: ");
        
        TextField tfConceptoGasto = new TextField();
        tfConceptoGasto.setPrefWidth(320);
        DatePicker dpFechaGasto = new DatePicker();
        dpFechaGasto.setPrefWidth(320);
        TextField tfMontoGasto = new TextField();
         ComboBox cbCatalogoConcepto = new ComboBox(lstConceptosGastos);//FXCollections.observableList(lstCategorias));
        cbCatalogoConcepto.setPrefWidth(140); 
        cbCatalogoConcepto.setOnAction((event) -> {
            tfConceptoGasto.setText(cbCatalogoConcepto.getValue().toString());
        });   
       
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction((ActionEvent e)->{
            if (vbAreaTrabajo.getChildren().size()>0){
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction((ActionEvent e)->{
            gasto gasIdent = new gasto();
            gasIdent.setConcepto(tfConceptoGasto.getText());
            gasIdent.setFecha(dpFechaGasto.getValue().toString());
            gasIdent.setFlag(1);
            gasIdent.setMonto(Float.parseFloat(tfMontoGasto.getText()));
            int resultado = gasDAO.insertarGasto(gasIdent);
            if (resultado == 1){
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setHeaderText("Informacion");
              alert.setTitle("Informacion");
              alert.setContentText("Gasto Resgistrado.");
              alert.show();
              removerVistas();
            }
            else{
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setHeaderText("Informacion");
              alert.setTitle("Informacion");
              alert.setContentText("Gasto No se Resgistro.");
            }
        });
        
        HBox hbBotones = new HBox(btnCancelar, btnGuardar);
        hbBotones.setSpacing(5);
        
        GridPane gpDatosGasto = new GridPane();
        gpDatosGasto.setPadding(new Insets(5, 5, 5, 5));
        gpDatosGasto.setVgap(10);
        gpDatosGasto.setHgap(10);
        gpDatosGasto.add(lbFechaGasto, 0, 0);
        gpDatosGasto.add(dpFechaGasto, 1, 0);
        gpDatosGasto.add(lbConceptoGasto, 0, 1);
        gpDatosGasto.add(tfConceptoGasto, 1, 1);
        gpDatosGasto.add(cbCatalogoConcepto, 2, 1);
        gpDatosGasto.add(lbMontoGasto, 0, 2);
        gpDatosGasto.add(tfMontoGasto, 1, 2);
        gpDatosGasto.add(hbBotones, 1, 3);
        
        vbPpal.getChildren().addAll(lbTituloVista, gpDatosGasto);
        return vbPpal;    
    }
    private VBox ventanaModificarGastos(){
        VBox vbPpal = new VBox();
        vbPpal.setAlignment(Pos.CENTER_LEFT);
        Label lbTituloVista = new Label("MODIFICAR GASTO");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        if (!lstConceptosGastos.isEmpty()){
            lstConceptosGastos = FXCollections.observableArrayList();
        }
        lstWhereConcepto.add("id_catalogo_gastos is not null");
        for(catalogoGasto lstCatGast: catGastoDAO.consultarCatGastos(lstWhereConcepto)){
               lstConceptosGastos.add(lstCatGast.getConcepto());
        }        
        //concepto, fecha, monto, flag
        TableView<gasto> tvGastos = new TableView();
        tvGastos.setMinWidth(480);
        tvGastos.setMaxHeight(480);
        tvGastos.setPrefWidth(480);
        
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgBusquedas = new ToggleGroup();
        
        RadioButton rbTodos = new RadioButton("Todos");
        RadioButton rbConcepto = new RadioButton("Concepto");
        RadioButton rbFecha = new RadioButton("Fecha");
        
        rbFecha.setToggleGroup(tgBusquedas);
        rbConcepto.setToggleGroup(tgBusquedas);
        rbTodos.setToggleGroup(tgBusquedas);        
        rbFecha.setSelected(true);
        
        Label lbFecha = new Label("Por Fecha: ");
        DatePicker dpFecha = new DatePicker(LocalDate.now());
        TextField tfConcepto = new TextField();
        Label lbConcepto = new Label("Por Concepto: ");
        Label lbCatalogoConcepto = new Label("Catalogo Conceptos: ");
        ComboBox cbCatalogoConcepto = new ComboBox(lstConceptosGastos);//FXCollections.observableList(lstCategorias));
        cbCatalogoConcepto.setPrefWidth(140);
        cbCatalogoConcepto.setOnAction((event) -> {
            tfConcepto.setText(cbCatalogoConcepto.getValue().toString());
        });
        
        HBox hbCompSeleccion = new HBox();
        Button btnBuscarGasto = new Button("Seleccionar");
        btnBuscarGasto.setMaxHeight(50);
        btnBuscarGasto.setOnAction((ActionEvent e)->{
         if (rbTodos.isSelected()){
           lstWhereConcepto.add("id_gasto is not null");
           tvGastos.setItems(gasDAO.consultarGasto(lstWhereConcepto));
         }    
            
         if (rbConcepto.isSelected()){
           lstWhereConcepto.add("concepto = '"+tfConcepto.getText()+"'");
           tvGastos.setItems(gasDAO.consultarGasto(lstWhereConcepto));
         }
         
         if (rbFecha.isSelected()){
           lstWhereConcepto.add("fecha = '"+dpFecha.getValue().toString()+"' ");
           tvGastos.setItems(gasDAO.consultarGasto(lstWhereConcepto));
         }
         
           String titulo = "MODIFICAR GASTO ("+String.valueOf(tvGastos.getItems().size())+" Seleccionados)";
           lbTituloVista.setText(titulo);
         
        });
        
        VBox vbConcepto = new VBox();
        VBox vbFecha = new VBox();
        VBox vbCatalogoConceptos = new VBox();
        vbConcepto.getChildren().addAll(lbConcepto, tfConcepto);
        vbCatalogoConceptos.getChildren().addAll(lbCatalogoConcepto, cbCatalogoConcepto);
        vbFecha.getChildren().addAll(lbFecha, dpFecha);
        
        
        hbCompSeleccion.getChildren().addAll(vbFecha, vbConcepto, vbCatalogoConceptos, btnBuscarGasto);
        hbCompSeleccion.setPadding(new Insets(10, 10, 10, 10));
        hbCompSeleccion.setSpacing(5);
        
        HBox hbTipoSeleccion = new HBox();
        hbTipoSeleccion.getChildren().addAll(rbFecha, rbConcepto, rbTodos);
        hbTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        hbTipoSeleccion.setSpacing(5);
        
        Label lbIdGastos = new Label("Id Gasto: ");
        Label lbConceptoGasto = new Label("Concepto: ");
        Label lbFechaGasto = new Label("Fecha: ");
        Label lbMontoGasto = new Label("Monto: ");
        
        TextField tfIdGasto = new TextField();
        tfIdGasto.setMaxWidth(120);
        tfIdGasto.setEditable(false);
        TextField tfConceptoGasto = new TextField();
        tfConceptoGasto.setPrefWidth(320);
        DatePicker dpFechaGasto = new DatePicker();
        dpFechaGasto.setPrefWidth(320);
        TextField tfMontoGasto = new TextField();

        TableColumn<gasto, Integer> idGastoColumna = new TableColumn<>("Id Gasto");
        idGastoColumna.setMinWidth(120);
        idGastoColumna.setCellValueFactory(new PropertyValueFactory<>("id_gasto"));

        TableColumn<gasto, String> conceptoGastoColumna = new TableColumn<>("concepto");
        conceptoGastoColumna.setMinWidth(120);
        conceptoGastoColumna.setCellValueFactory(new PropertyValueFactory<>("concepto"));        
        
        TableColumn<gasto, String> fechaGastoColumna = new TableColumn<>("Fecha");
        fechaGastoColumna.setMinWidth(120);
        fechaGastoColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));        
        
        TableColumn<gasto, Integer> montoGastoColumna = new TableColumn<>("Monto");
        montoGastoColumna.setMinWidth(120);
        montoGastoColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));   
        
        tvGastos.getColumns().addAll(idGastoColumna, conceptoGastoColumna, montoGastoColumna, fechaGastoColumna);
        lstWhereConcepto.add("fecha = '"+dpFecha.getValue().toString()+"'");
        tvGastos.setItems(gasDAO.consultarGasto(lstWhereConcepto));
        String titulo = "MODIFICAR GASTO ("+String.valueOf(tvGastos.getItems().size())+" Seleccionados)";
        lbTituloVista.setText(titulo);
        
        tvGastos.setOnMouseClicked((event) -> {
            gasto gasTemp = tvGastos.getSelectionModel().getSelectedItem();
            tfIdGasto.setText(String.valueOf(gasTemp.getId_gasto()));
            tfConceptoGasto.setText(gasTemp.getConcepto());
            tfMontoGasto.setText(String.valueOf(gasTemp.getMonto()));
            dpFechaGasto.setValue(LocalDate.parse(gasTemp.getFecha()));
        });
        
        /*lstWhereConcepto.add("id_catalogo_gastos is not null");
        for(catalogoGasto lstCatGast: catGastoDAO.consultarCatGastos(lstWhereConcepto)){
               lstConceptosGastos.add(lstCatGast.getConcepto());
        }*/
        
        ComboBox cbSelecCatalogoConcepto = new ComboBox(lstConceptosGastos);//FXCollections.observableList(lstCategorias));
        cbSelecCatalogoConcepto.setPrefWidth(140); 
        cbSelecCatalogoConcepto.setOnAction((event) -> {
            tfConceptoGasto.setText(cbSelecCatalogoConcepto.getValue().toString());
        });   
       
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction((ActionEvent e)->{
            if (vbAreaTrabajo.getChildren().size()>0){
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        Button btnModificar = new Button("Modificar");
        btnModificar.setOnAction((ActionEvent e)->{
            gasto gastTemp = new gasto();
            gastTemp.setId_gasto(Integer.parseInt(tfIdGasto.getText()));
            gastTemp.setConcepto(tfConceptoGasto.getText());
            gastTemp.setFecha(dpFechaGasto.getValue().toString());
            gastTemp.setMonto(Float.valueOf(tfMontoGasto.getText()));
            gasDAO.modificarGasto(gastTemp);
            Alert aleMensaje = new Alert(Alert.AlertType.INFORMATION);
            aleMensaje.setContentText("Gasto Actualizado.");
            aleMensaje.setTitle("Informacion");
            aleMensaje.show();
        });
        
        HBox hbBotones = new HBox(btnCancelar, btnModificar);
        hbBotones.setSpacing(5);
        
        GridPane gpDatosGasto = new GridPane();
        gpDatosGasto.setPadding(new Insets(5, 5, 5, 5));
        gpDatosGasto.setVgap(10);
        gpDatosGasto.setHgap(10);
        gpDatosGasto.add(lbIdGastos, 0, 0);
        gpDatosGasto.add(tfIdGasto, 1, 0);
        gpDatosGasto.add(lbFechaGasto, 0, 1);
        gpDatosGasto.add(dpFechaGasto, 1, 1);
        gpDatosGasto.add(lbConceptoGasto, 0, 2);
        gpDatosGasto.add(tfConceptoGasto, 1, 2);
        gpDatosGasto.add(cbSelecCatalogoConcepto, 2, 2);
        gpDatosGasto.add(lbMontoGasto, 0, 3);
        gpDatosGasto.add(tfMontoGasto, 1, 3);
        gpDatosGasto.add(hbBotones, 1, 4);
        
        vbPpal.getChildren().addAll(lbTituloVista, hbTipoSeleccion, hbCompSeleccion, tvGastos, gpDatosGasto);
        return vbPpal;    
    }
    private VBox ventanaEliminarGastos(){
        VBox vbPpal = new VBox();
        vbPpal.setAlignment(Pos.CENTER_LEFT);
        Label lbTituloVista = new Label("ELIMINAR GASTO");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        if (!lstConceptosGastos.isEmpty()){
            lstConceptosGastos = FXCollections.observableArrayList();
        }        
        lstWhereConcepto.add("id_catalogo_gastos is not null");
        for(catalogoGasto lstCatGast: catGastoDAO.consultarCatGastos(lstWhereConcepto)){
               lstConceptosGastos.add(lstCatGast.getConcepto());
        }
        
        //id_gasto
        //concepto
	//fecha
	//monto
	//flag
        TableView<gasto> tvGastos = new TableView();
        tvGastos.setMaxHeight(520);
        
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgBusquedas = new ToggleGroup();
        
        RadioButton rbTodos = new RadioButton("Todos");
        RadioButton rbConcepto = new RadioButton("Concepto");
        RadioButton rbFecha = new RadioButton("Fecha");
        
        rbFecha.setToggleGroup(tgBusquedas);
        rbConcepto.setToggleGroup(tgBusquedas);
        rbTodos.setToggleGroup(tgBusquedas);        
        rbFecha.setSelected(true);
        
        Label lbFecha = new Label("Por Fecha: ");
        DatePicker dpFecha = new DatePicker(LocalDate.now());
        TextField tfConcepto = new TextField();
        Label lbConcepto = new Label("Por Concepto: ");
        Label lbCatalogoConcepto = new Label("Catalogo Conceptos: ");
        ComboBox cbCatalogoConcepto = new ComboBox(lstConceptosGastos);//FXCollections.observableList(lstCategorias));
        cbCatalogoConcepto.setPrefWidth(140);
        cbCatalogoConcepto.setOnAction((event) -> {
            tfConcepto.setText(cbCatalogoConcepto.getValue().toString());
        });
        
        HBox hbCompSeleccion = new HBox();
        Button btnBuscarGasto = new Button("Seleccionar");
        btnBuscarGasto.setMaxHeight(50);
        btnBuscarGasto.setOnAction((ActionEvent e)->{
         
          if (rbTodos.isSelected()){
           lstWhereConcepto.add("id_gasto is not null");
           tvGastos.setItems(gasDAO.consultarGasto(lstWhereConcepto));
         }    
            
         if (rbConcepto.isSelected()){
           lstWhereConcepto.add("concepto = '"+tfConcepto.getText()+"'");
           tvGastos.setItems(gasDAO.consultarGasto(lstWhereConcepto));
         }
         
         if (rbFecha.isSelected()){
           lstWhereConcepto.add("fecha = '"+dpFecha.getValue().toString()+"' ");
           tvGastos.setItems(gasDAO.consultarGasto(lstWhereConcepto));
         }
         String titulo = "MODIFICAR GASTO ("+String.valueOf(tvGastos.getItems().size())+" Seleccionados)";
         lbTituloVista.setText(titulo);
         
        });
        
        VBox vbConcepto = new VBox();
        VBox vbFecha = new VBox();
        VBox vbCatalogoConceptos = new VBox();
        vbConcepto.getChildren().addAll(lbConcepto, tfConcepto);
        vbCatalogoConceptos.getChildren().addAll(lbCatalogoConcepto, cbCatalogoConcepto);
        vbFecha.getChildren().addAll(lbFecha, dpFecha);
        
        hbCompSeleccion.getChildren().addAll(vbFecha, vbConcepto, vbCatalogoConceptos, btnBuscarGasto);
        hbCompSeleccion.setPadding(new Insets(10, 10, 10, 10));
        hbCompSeleccion.setSpacing(5);
        
        HBox hbTipoSeleccion = new HBox();
        hbTipoSeleccion.getChildren().addAll(rbFecha, rbConcepto, rbTodos);
        hbTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        hbTipoSeleccion.setSpacing(5);
        
        TableColumn<gasto, Integer> idGastoColumna = new TableColumn<>("Id Gasto");
        idGastoColumna.setMinWidth(120);
        idGastoColumna.setCellValueFactory(new PropertyValueFactory<>("id_gasto"));

        TableColumn<gasto, String> conceptoGastoColumna = new TableColumn<>("concepto");
        conceptoGastoColumna.setMinWidth(120);
        conceptoGastoColumna.setCellValueFactory(new PropertyValueFactory<>("concepto"));        
        
        TableColumn<gasto, String> fechaGastoColumna = new TableColumn<>("Fecha");
        fechaGastoColumna.setMinWidth(120);
        fechaGastoColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));        
        
        TableColumn<gasto, Integer> montoGastoColumna = new TableColumn<>("Monto");
        montoGastoColumna.setMinWidth(120);
        montoGastoColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));   
        
        tvGastos.getColumns().addAll(idGastoColumna, conceptoGastoColumna, montoGastoColumna, fechaGastoColumna);
        lstWhereConcepto.add("fecha = '"+dpFecha.getValue().toString()+"' ");
        tvGastos.setItems(gasDAO.consultarGasto(lstWhereConcepto));
        String titulo = "MODIFICAR GASTO ("+String.valueOf(tvGastos.getItems().size())+" Seleccionados)";
        lbTituloVista.setText(titulo);
        
        Label lbIdGastos = new Label("Id Gasto: ");
        Label lbConceptoGasto = new Label("Concepto: ");
        Label lbFechaGasto = new Label("Fecha: ");
        Label lbMontoGasto = new Label("Monto: ");
        
        TextField tfIdGasto = new TextField();
        tfIdGasto.setMaxWidth(120);
        tfIdGasto.setEditable(false);
        TextField tfConceptoGasto = new TextField();
        tfConceptoGasto.setPrefWidth(320);
        tfConceptoGasto.setEditable(false);
        DatePicker dpFechaGasto = new DatePicker();
        dpFechaGasto.setPrefWidth(320);
        dpFechaGasto.setEditable(false);
        TextField tfMontoGasto = new TextField();
        tfMontoGasto.setEditable(false);
        
         tvGastos.setOnMouseClicked((event) -> {
            gasto gasTemp = tvGastos.getSelectionModel().getSelectedItem();
            tfIdGasto.setText(String.valueOf(gasTemp.getId_gasto()));
            tfConceptoGasto.setText(gasTemp.getConcepto());
            tfMontoGasto.setText(String.valueOf(gasTemp.getMonto()));
            dpFechaGasto.setValue(LocalDate.parse(gasTemp.getFecha()));
        });
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction((ActionEvent e)->{
            if (vbAreaTrabajo.getChildren().size()>0){
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        Button btnModificar = new Button("Modificar");
        btnModificar.setOnAction((ActionEvent e)->{
        });
        
        HBox hbBotones = new HBox(btnCancelar, btnModificar);
        hbBotones.setSpacing(5);
        
        GridPane gpDatosGasto = new GridPane();
        gpDatosGasto.setPadding(new Insets(5, 5, 5, 5));
        gpDatosGasto.setVgap(10);
        gpDatosGasto.setHgap(10);
        gpDatosGasto.add(lbIdGastos, 0, 0);
        gpDatosGasto.add(tfIdGasto, 1, 0);
        gpDatosGasto.add(lbFechaGasto, 0, 1);
        gpDatosGasto.add(dpFechaGasto, 1, 1);
        gpDatosGasto.add(lbConceptoGasto, 0, 2);
        gpDatosGasto.add(tfConceptoGasto, 1, 2);
        gpDatosGasto.add(lbMontoGasto, 0, 3);
        gpDatosGasto.add(tfMontoGasto, 1, 3);
        gpDatosGasto.add(hbBotones, 1, 4);
        
        vbPpal.getChildren().addAll(lbTituloVista, hbTipoSeleccion, hbCompSeleccion, tvGastos, gpDatosGasto);
        return vbPpal;    
    }   
    private VBox ventanaConsultarGastos(){
        VBox vbPpal = new VBox();
        vbPpal.setAlignment(Pos.CENTER_LEFT);
        Label lbTituloVista = new Label("CONSULTAR GASTOS");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
         if (!lstConceptosGastos.isEmpty()){
            lstConceptosGastos = FXCollections.observableArrayList();
        }       
        lstWhereConcepto.add("id_catalogo_gastos is not null");
        for(catalogoGasto lstCatGast: catGastoDAO.consultarCatGastos(lstWhereConcepto)){
               lstConceptosGastos.add(lstCatGast.getConcepto());
        }
        
        //id_gasto
        //concepto
	//fecha
	//monto
	//flag
        TableView<gasto> tvGastos = new TableView();
        tvGastos.setMaxHeight(520);
        
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgBusquedas = new ToggleGroup();
        
        RadioButton rbTodos = new RadioButton("Todos");
        RadioButton rbConcepto = new RadioButton("Concepto");
        RadioButton rbFecha = new RadioButton("Fecha");
        
        rbFecha.setToggleGroup(tgBusquedas);
        rbConcepto.setToggleGroup(tgBusquedas);
        rbTodos.setToggleGroup(tgBusquedas);        
        rbFecha.setSelected(true);
        
        Label lbFecha = new Label("Por Fecha: ");
        DatePicker dpFecha = new DatePicker(LocalDate.now());
        TextField tfConcepto = new TextField();
        Label lbConcepto = new Label("Por Concepto: ");
        Label lbCatalogoConcepto = new Label("Catalogo Conceptos: ");
        ComboBox cbCatalogoConcepto = new ComboBox(lstConceptosGastos);//FXCollections.observableList(lstCategorias));
        cbCatalogoConcepto.setPrefWidth(140); 
        cbCatalogoConcepto.setOnAction((event) -> {
            tfConcepto.setText(cbCatalogoConcepto.getValue().toString());
        });
        
        HBox hbCompSeleccion = new HBox();
        Button btnBuscarGasto = new Button("Seleccionar");
        btnBuscarGasto.setMaxHeight(50);
        btnBuscarGasto.setOnAction((ActionEvent e)->{
         
          if (rbTodos.isSelected()){
           lstWhereConcepto.add("id_gasto is not null");
           tvGastos.setItems(gasDAO.consultarGasto(lstWhereConcepto));
         }    
            
         if (rbConcepto.isSelected()){
           lstWhereConcepto.add("concepto = '"+tfConcepto.getText()+"'");
           tvGastos.setItems(gasDAO.consultarGasto(lstWhereConcepto));
         }
         
         if (rbFecha.isSelected()){
           lstWhereConcepto.add("fecha = '"+dpFecha.getValue().toString()+"' ");
           tvGastos.setItems(gasDAO.consultarGasto(lstWhereConcepto));
         }
         
           String titulo = "MODIFICAR GASTO ("+String.valueOf(tvGastos.getItems().size())+" Seleccionados)";
           lbTituloVista.setText(titulo);
         
        });
        
        VBox vbConcepto = new VBox();
        VBox vbFecha = new VBox();
        VBox vbCatalogoConceptos = new VBox();
        vbConcepto.getChildren().addAll(lbConcepto, tfConcepto);
        vbCatalogoConceptos.getChildren().addAll(lbCatalogoConcepto, cbCatalogoConcepto);
        vbFecha.getChildren().addAll(lbFecha, dpFecha);
        
        
        hbCompSeleccion.getChildren().addAll(vbFecha, vbConcepto, vbCatalogoConceptos, btnBuscarGasto);
        hbCompSeleccion.setPadding(new Insets(10, 10, 10, 10));
        hbCompSeleccion.setSpacing(5);
        
        HBox hbTipoSeleccion = new HBox();
        hbTipoSeleccion.getChildren().addAll(rbFecha, rbConcepto, rbTodos);
        hbTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        hbTipoSeleccion.setSpacing(5);
        
        TableColumn<gasto, Integer> idGastoColumna = new TableColumn<>("Id Gasto");
        idGastoColumna.setMinWidth(120);
        idGastoColumna.setCellValueFactory(new PropertyValueFactory<>("id_gasto"));

        TableColumn<gasto, String> conceptoGastoColumna = new TableColumn<>("concepto");
        conceptoGastoColumna.setMinWidth(120);
        conceptoGastoColumna.setCellValueFactory(new PropertyValueFactory<>("concepto"));        
        
        TableColumn<gasto, String> fechaGastoColumna = new TableColumn<>("Fecha");
        fechaGastoColumna.setMinWidth(120);
        fechaGastoColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));        
        
        TableColumn<gasto, Integer> montoGastoColumna = new TableColumn<>("Monto");
        montoGastoColumna.setMinWidth(120);
        montoGastoColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));   
        
        tvGastos.getColumns().addAll(idGastoColumna, conceptoGastoColumna, montoGastoColumna, fechaGastoColumna);
        lstWhereConcepto.add("fecha = '"+dpFecha.getValue().toString()+"' ");
        tvGastos.setItems(gasDAO.consultarGasto(lstWhereConcepto));
        String titulo = "MODIFICAR GASTO ("+String.valueOf(tvGastos.getItems().size())+" Seleccionados)";
        lbTituloVista.setText(titulo);
        
        Label lbIdGastos = new Label("Id Gasto: ");
        Label lbConceptoGasto = new Label("Concepto: ");
        Label lbFechaGasto = new Label("Fecha: ");
        Label lbMontoGasto = new Label("Monto: ");
        
        TextField tfIdGasto = new TextField();
        tfIdGasto.setMaxWidth(120);
        TextField tfConceptoGasto = new TextField();
        tfConceptoGasto.setPrefWidth(320);
        DatePicker dpFechaGasto = new DatePicker();
        dpFechaGasto.setPrefWidth(320);
        TextField tfMontoGasto = new TextField();

         tvGastos.setOnMouseClicked((event) -> {
            gasto gasTemp = tvGastos.getSelectionModel().getSelectedItem();
            tfIdGasto.setText(String.valueOf(gasTemp.getId_gasto()));
            tfConceptoGasto.setText(gasTemp.getConcepto());
            tfMontoGasto.setText(String.valueOf(gasTemp.getMonto()));
            dpFechaGasto.setValue(LocalDate.parse(gasTemp.getFecha()));
        });
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction((ActionEvent e)->{
            if (vbAreaTrabajo.getChildren().size()>0){
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        Button btnPDF = new Button("Reporte PDF");
        btnPDF.setOnAction((ActionEvent e)->{
         try{
            
           /* User home directory location */
            //String userHomeDirectory = System.getProperty("user.home");
            /* Output file location */
            
            File file;
            JasperReport jasperReport;
            file = new File("Reportes/Formatos/reporteGastos.jasper");
            jasperReport = (JasperReport) JRLoader.loadObject(file);
            LocalDateTime ld = LocalDateTime.now();
            String fechaFile = String.valueOf(ld.getDayOfMonth())+String.valueOf(ld.getMonth())+String.valueOf(ld.getYear())+String.valueOf(ld.getHour())+String.valueOf(ld.getMinute())+String.valueOf(ld.getSecond());
            //String outputFile = userHomeDirectory + File.separatorChar + "ReporteGastos"+fechaFile+".pdf";
            String outputFile = "Reportes/Gastos/" + File.separatorChar + "ReporteGastos"+fechaFile+".pdf";
           //JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tvProductos.getItems().subList(0, tvProductos.getItems().size()-1));
           JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tvGastos.getItems());
           System.out.println("Hay "+tvGastos.getItems().size());
           Map<String, Object> parameters = new HashMap<>();
           parameters.put("ItemsDataSource", itemsJRBean);
           //parameters.put("Sucursal", "Veracruz");
           //parameters.put("Fecha", LocalDate.now().toString());
           /* Generando el PDF */
            //C:\Users\dopcan\Documents\NetBeansProjects\ClasesConsultorio\src\gestionconsultorio
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            /* outputStream to create PDF */
            OutputStream outputStream = new FileOutputStream(new File(outputFile));
            /* Write content to PDF file */
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            outputStream.close();
              Alert resp = new Alert(Alert.AlertType.INFORMATION);
              resp.setTitle("Informacion");
              resp.setContentText("Reporte Generado carpeta Reportes/Gastos!! ");
              resp.showAndWait();
            
        } catch (JRException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
                 Logger.getLogger(CRMPLAMAR.class.getName()).log(Level.SEVERE, null, ex);
             }            
        });

        Button btnOtrosFmto = new Button("Otros Formatos");
        btnOtrosFmto.setOnAction((ActionEvent e)->{
          try{
            
           /* User home directory location */
            String userHomeDirectory = System.getProperty("user.home");
            /* Output file location */
            
            File file;
            JasperReport jasperReport;
            file = new File("Reportes/Formatos/reporteGastos.jasper");
            jasperReport = (JasperReport) JRLoader.loadObject(file);
            LocalDateTime ld = LocalDateTime.now();
            String fechaFile = String.valueOf(ld.getDayOfMonth())+String.valueOf(ld.getMonth())+String.valueOf(ld.getYear())+String.valueOf(ld.getHour())+String.valueOf(ld.getMinute())+String.valueOf(ld.getSecond());
            String outputFile = userHomeDirectory + File.separatorChar + "ReporteInventario"+fechaFile+".pdf";
           //JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tvProductos.getItems().subList(0, tvProductos.getItems().size()-1));
           JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tvGastos.getItems());
           System.out.println("Hay "+tvGastos.getItems().size());
           Map<String, Object> parameters = new HashMap<>();
           parameters.put("ItemsDataSource", itemsJRBean);
           //parameters.put("Sucursal", "Veracruz");
           //parameters.put("Fecha", LocalDate.now().toString());
           /* Generando el PDF */
            //C:\Users\dopcan\Documents\NetBeansProjects\ClasesConsultorio\src\gestionconsultorio
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            JRViewer jrViewer;
            JPanel jpanel;
            SwingNode swingNode;
            jpanel = new JPanel();
            swingNode = new SwingNode();
            jrViewer = new JRViewer(jasperPrint);
            jrViewer.setBounds(0, 0, 1200, 800);
            jpanel.setLayout(null);
            jpanel.add(jrViewer);
            jpanel.setSize(1200, 800);
            Pane panePreview = new Pane(); 
            panePreview.setPrefSize(1200, 800);
            panePreview.getChildren().add(swingNode);
            swingNode.setContent(jpanel);


            StackPane rootSelectClientes = new StackPane();
            rootSelectClientes.getChildren().addAll(swingNode);
       
            Scene scene = new Scene(rootSelectClientes,1200,800);
            Stage stgPpal = new Stage();
            stgPpal.setScene(scene);
            stgPpal.initModality(Modality.WINDOW_MODAL);
            stgPpal.show();  
        } catch (JRException ex) {
            ex.printStackTrace();
        }           
        });
        
        HBox hbBotones = new HBox(btnCancelar, btnPDF, btnOtrosFmto);
        hbBotones.setSpacing(5);
        
        GridPane gpDatosGasto = new GridPane();
        gpDatosGasto.setPadding(new Insets(5, 5, 5, 5));
        gpDatosGasto.setVgap(10);
        gpDatosGasto.setHgap(10);
        gpDatosGasto.add(lbIdGastos, 0, 0);
        gpDatosGasto.add(tfIdGasto, 1, 0);
        gpDatosGasto.add(lbFechaGasto, 0, 1);
        gpDatosGasto.add(dpFechaGasto, 1, 1);
        gpDatosGasto.add(lbConceptoGasto, 0, 2);
        gpDatosGasto.add(tfConceptoGasto, 1, 2);
        gpDatosGasto.add(lbMontoGasto, 0, 3);
        gpDatosGasto.add(tfMontoGasto, 1, 3);
        gpDatosGasto.add(hbBotones, 1, 4);
        
        vbPpal.getChildren().addAll(lbTituloVista, hbTipoSeleccion, hbCompSeleccion, tvGastos, gpDatosGasto);
        return vbPpal;    
    }   
    private VBox ventanaAgregarConceptos(){
        VBox vbPpal = new VBox();
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("GREGAR CONCEPTOS PARA GASTOS");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
         //id_catologo_gastos, concepto, flag
        Label lbConceptoGasto = new Label("Concepto: ");
        
        TextField tfConceptoGasto = new TextField();
        tfConceptoGasto.setPrefWidth(320);

        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction((ActionEvent e)->{
            if (vbAreaTrabajo.getChildren().size()>0){
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction((ActionEvent e)->{
            catalogoGasto catGasIdent = new catalogoGasto();
            catGasIdent.setConcepto(tfConceptoGasto.getText());
            catGasIdent.setFlag(1);
            int resultado = catGastoDAO.insertarCatGastos(catGasIdent);
            if (resultado > 0){
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setHeaderText("Informacion");
              alert.setTitle("Informacion");
              alert.setContentText("Concepto Gasto Resgistrado.");
              alert.show();
              removerVistas();
            }
            else{
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setHeaderText("Informacion");
              alert.setTitle("Informacion");
              alert.setContentText("Concepto Gasto No se Resgistro.");
            }
        });
        
        HBox hbBotones = new HBox(btnCancelar, btnGuardar);
        hbBotones.setSpacing(5);
        
        GridPane gpDatosGasto = new GridPane();
        gpDatosGasto.setPadding(new Insets(5, 5, 5, 5));
        gpDatosGasto.setVgap(10);
        gpDatosGasto.setHgap(10);
        gpDatosGasto.add(lbConceptoGasto, 0, 1);
        gpDatosGasto.add(tfConceptoGasto, 1, 1);
        gpDatosGasto.add(hbBotones, 2, 1);
        
        vbPpal.getChildren().addAll(lbTituloVista, gpDatosGasto);
        return vbPpal;
     }
    private VBox ventanaModificarConceptosGastos(){
        VBox vbPpal = new VBox();
        vbPpal.setAlignment(Pos.CENTER_LEFT);
        Label lbTituloVista = new Label("MODIFICAR CONCEPTOS DE GASTOS");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
         //id_catologo_gastos, concepto, flag
        lstWhereConcepto.add("id_catalogo_gastos is not null");
        for(catalogoGasto lstCatGast: catGastoDAO.consultarCatGastos(lstWhereConcepto)){
               lstConceptosGastos.add(lstCatGast.getConcepto());
        }
         
        TableView<catalogoGasto> tvCatalogoGastos = new TableView();
        tvCatalogoGastos.setMinWidth(480);
        tvCatalogoGastos.setMaxHeight(480);
        tvCatalogoGastos.setPrefWidth(480);
        
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgBusquedas = new ToggleGroup();
        
        RadioButton rbTodos = new RadioButton("Todos");
        RadioButton rbConcepto = new RadioButton("Concepto");
        
        rbConcepto.setToggleGroup(tgBusquedas);
        rbTodos.setToggleGroup(tgBusquedas);        
        rbTodos.setSelected(true);
        
        TextField tfConcepto = new TextField();
        Label lbConcepto = new Label("Por Concepto: ");
        Label lbCatalogoConcepto = new Label("Catalogo Conceptos: ");
        ComboBox cbCatalogoConcepto = new ComboBox(lstConceptosGastos);//FXCollections.observableList(lstCategorias));
        cbCatalogoConcepto.setPrefWidth(140);        
        cbCatalogoConcepto.setOnAction((event) -> {
            tfConcepto.setText(cbCatalogoConcepto.getValue().toString());
        });
        
        HBox hbCompSeleccion = new HBox();
        Button btnBuscarGasto = new Button("Seleccionar");
        btnBuscarGasto.setMaxHeight(50);
        btnBuscarGasto.setOnAction((ActionEvent e)->{
         if (rbTodos.isSelected()){
           lstWhereConcepto.add("id_catalogo_gastos is not null");
           tvCatalogoGastos.setItems(catGastoDAO.consultarCatGastos(lstWhereConcepto));
         }    
            
         if (rbConcepto.isSelected()){
           lstWhereConcepto.add("concepto = '"+tfConcepto.getText()+"'");
           tvCatalogoGastos.setItems(catGastoDAO.consultarCatGastos(lstWhereConcepto));
         }
         
         
           String titulo = "MODIFICAR CONCEPTOS DE GASTOS ("+String.valueOf(tvCatalogoGastos.getItems().size())+" Seleccionados)";
           lbTituloVista.setText(titulo);
         
        });
        
        VBox vbConcepto = new VBox();
        VBox vbCatalogoConceptos = new VBox();
        vbConcepto.getChildren().addAll(lbConcepto, tfConcepto);
        vbCatalogoConceptos.getChildren().addAll(lbCatalogoConcepto, cbCatalogoConcepto);
        
        
        hbCompSeleccion.getChildren().addAll(vbConcepto, vbCatalogoConceptos, btnBuscarGasto);
        hbCompSeleccion.setPadding(new Insets(10, 10, 10, 10));
        hbCompSeleccion.setSpacing(5);
        
        HBox hbTipoSeleccion = new HBox();
        hbTipoSeleccion.getChildren().addAll(rbConcepto, rbTodos);
        hbTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        hbTipoSeleccion.setSpacing(5);
        
        Label lbIdGastos = new Label("Id Catalogo Gasto: ");
        Label lbConceptoGasto = new Label("Concepto: ");
        
        TextField tfIdGasto = new TextField();
        tfIdGasto.setMaxWidth(120);
        tfIdGasto.setEditable(false);
        TextField tfConceptoGasto = new TextField();
        tfConceptoGasto.setPrefWidth(320);
        //id_catologo_gastos, concepto, flag
        TableColumn<catalogoGasto, Integer> idConceptoGastoColumna = new TableColumn<>("Id Concepto Gasto");
        idConceptoGastoColumna.setMinWidth(120);
        idConceptoGastoColumna.setCellValueFactory(new PropertyValueFactory<>("id_catalogo_gastos"));

        TableColumn<catalogoGasto, String> conceptoGastoColumna = new TableColumn<>("Concepto");
        conceptoGastoColumna.setMinWidth(120);
        conceptoGastoColumna.setCellValueFactory(new PropertyValueFactory<>("concepto"));        
        
        tvCatalogoGastos.getColumns().addAll(idConceptoGastoColumna, conceptoGastoColumna);
        lstWhereConcepto.add("id_catalogo_gastos is not null");
        tvCatalogoGastos.setItems(catGastoDAO.consultarCatGastos(lstWhereConcepto));
        String titulo = "MODIFICAR CONCEPTOS DE GASTOS ("+String.valueOf(tvCatalogoGastos.getItems().size())+" Seleccionados)";
        lbTituloVista.setText(titulo);
        
        tvCatalogoGastos.setOnMouseClicked((event) -> {
            catalogoGasto catGasTemp = tvCatalogoGastos.getSelectionModel().getSelectedItem();
            tfIdGasto.setText(String.valueOf(catGasTemp.getId_catalogo_gastos()));
            tfConceptoGasto.setText(catGasTemp.getConcepto());
        });
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction((ActionEvent e)->{
            if (vbAreaTrabajo.getChildren().size()>0){
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        Button btnModificar = new Button("Modificar");
        btnModificar.setOnAction((ActionEvent e)->{
            catalogoGasto catGastTemp = new catalogoGasto();
            catGastTemp.setId_catalogo_gastos(Integer.parseInt(tfIdGasto.getText()));
            catGastTemp.setConcepto(tfConceptoGasto.getText());
            catGastoDAO.modificarCatGastos(catGastTemp);
            Alert aleMensaje = new Alert(Alert.AlertType.INFORMATION);
            aleMensaje.setContentText("Gasto Actualizado.");
            aleMensaje.setTitle("Informacion");
            aleMensaje.show();
        });
        
        HBox hbBotones = new HBox(btnCancelar, btnModificar);
        hbBotones.setSpacing(5);
        
        GridPane gpDatosGasto = new GridPane();
        gpDatosGasto.setPadding(new Insets(5, 5, 5, 5));
        gpDatosGasto.setVgap(10);
        gpDatosGasto.setHgap(10);
        gpDatosGasto.add(lbIdGastos, 0, 0);
        gpDatosGasto.add(tfIdGasto, 1, 0);
        gpDatosGasto.add(lbConceptoGasto, 0, 2);
        gpDatosGasto.add(tfConceptoGasto, 1, 2);
        gpDatosGasto.add(hbBotones, 1, 4);
        
        vbPpal.getChildren().addAll(lbTituloVista, hbTipoSeleccion, hbCompSeleccion, tvCatalogoGastos, gpDatosGasto);
        return vbPpal;    
    }
    private VBox ventanaEliminarConceptosGastos(){
        VBox vbPpal = new VBox();
        vbPpal.setAlignment(Pos.CENTER_LEFT);
        Label lbTituloVista = new Label("ELIMINAR CONCEPTOS DE GASTOS");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
         //id_catologo_gastos, concepto, flag
        lstWhereConcepto.add("id_catalogo_gastos is not null");
        for(catalogoGasto lstCatGast: catGastoDAO.consultarCatGastos(lstWhereConcepto)){
               lstConceptosGastos.add(lstCatGast.getConcepto());
        }
         
        TableView<catalogoGasto> tvCatalogoGastos = new TableView();
        tvCatalogoGastos.setMinWidth(480);
        tvCatalogoGastos.setMaxHeight(480);
        tvCatalogoGastos.setPrefWidth(480);
        
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgBusquedas = new ToggleGroup();
        
        RadioButton rbTodos = new RadioButton("Todos");
        RadioButton rbConcepto = new RadioButton("Concepto");
        
        rbConcepto.setToggleGroup(tgBusquedas);
        rbTodos.setToggleGroup(tgBusquedas);        
        rbTodos.setSelected(true);
        
        TextField tfConcepto = new TextField();
        Label lbConcepto = new Label("Por Concepto: ");
        Label lbCatalogoConcepto = new Label("Catalogo Conceptos: ");
        ComboBox cbCatalogoConcepto = new ComboBox(lstConceptosGastos);//FXCollections.observableList(lstCategorias));
        cbCatalogoConcepto.setPrefWidth(140);        
        cbCatalogoConcepto.setOnAction((event) -> {
            tfConcepto.setText(cbCatalogoConcepto.getValue().toString());
        });
        
        HBox hbCompSeleccion = new HBox();
        Button btnBuscarGasto = new Button("Seleccionar");
        btnBuscarGasto.setMaxHeight(50);
        btnBuscarGasto.setOnAction((ActionEvent e)->{
         if (rbTodos.isSelected()){
           lstWhereConcepto.add("id_catalogo_gastos is not null");
           tvCatalogoGastos.setItems(catGastoDAO.consultarCatGastos(lstWhereConcepto));
         }    
            
         if (rbConcepto.isSelected()){
           lstWhereConcepto.add("concepto = '"+tfConcepto.getText()+"'");
           tvCatalogoGastos.setItems(catGastoDAO.consultarCatGastos(lstWhereConcepto));
         }
         
         
           String titulo = "ELIMINAR CONCEPTOS DE GASTOS ("+String.valueOf(tvCatalogoGastos.getItems().size())+" Seleccionados)";
           lbTituloVista.setText(titulo);
         
        });
        
        VBox vbConcepto = new VBox();
        VBox vbCatalogoConceptos = new VBox();
        vbConcepto.getChildren().addAll(lbConcepto, tfConcepto);
        vbCatalogoConceptos.getChildren().addAll(lbCatalogoConcepto, cbCatalogoConcepto);
        
        
        hbCompSeleccion.getChildren().addAll(vbConcepto, vbCatalogoConceptos, btnBuscarGasto);
        hbCompSeleccion.setPadding(new Insets(10, 10, 10, 10));
        hbCompSeleccion.setSpacing(5);
        
        HBox hbTipoSeleccion = new HBox();
        hbTipoSeleccion.getChildren().addAll(rbConcepto, rbTodos);
        hbTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        hbTipoSeleccion.setSpacing(5);
        
        Label lbIdGastos = new Label("Id Catalogo Gasto: ");
        Label lbConceptoGasto = new Label("Concepto: ");
        
        TextField tfIdGasto = new TextField();
        tfIdGasto.setMaxWidth(120);
        tfIdGasto.setEditable(false);
        TextField tfConceptoGasto = new TextField();
        tfConceptoGasto.setPrefWidth(320);
        tfConceptoGasto.setEditable(false);
        //id_catologo_gastos, concepto, flag
        TableColumn<catalogoGasto, Integer> idConceptoGastoColumna = new TableColumn<>("Id Concepto Gasto");
        idConceptoGastoColumna.setMinWidth(120);
        idConceptoGastoColumna.setCellValueFactory(new PropertyValueFactory<>("id_catalogo_gastos"));

        TableColumn<catalogoGasto, String> conceptoGastoColumna = new TableColumn<>("Concepto");
        conceptoGastoColumna.setMinWidth(120);
        conceptoGastoColumna.setCellValueFactory(new PropertyValueFactory<>("concepto"));        
        
        tvCatalogoGastos.getColumns().addAll(idConceptoGastoColumna, conceptoGastoColumna);
        lstWhereConcepto.add("id_catalogo_gastos is not null");
        tvCatalogoGastos.setItems(catGastoDAO.consultarCatGastos(lstWhereConcepto));
        String titulo = "MODIFICAR CONCEPTOS DE GASTOS ("+String.valueOf(tvCatalogoGastos.getItems().size())+" Seleccionados)";
        lbTituloVista.setText(titulo);
        
        tvCatalogoGastos.setOnMouseClicked((event) -> {
            catalogoGasto catGasTemp = tvCatalogoGastos.getSelectionModel().getSelectedItem();
            tfIdGasto.setText(String.valueOf(catGasTemp.getId_catalogo_gastos()));
            tfConceptoGasto.setText(catGasTemp.getConcepto());
        });
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction((ActionEvent e)->{
            if (vbAreaTrabajo.getChildren().size()>0){
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        Button btnModificar = new Button("Eliminar");
        btnModificar.setOnAction((ActionEvent e)->{
            catGastoDAO.borrarCatGastos(Integer.parseInt(tfIdGasto.getText()));
            Alert aleMensaje = new Alert(Alert.AlertType.INFORMATION);
            aleMensaje.setContentText("Concepto Eliminado. Log");
            aleMensaje.setTitle("Informacion");
            aleMensaje.show();
        });
        
        HBox hbBotones = new HBox(btnCancelar, btnModificar);
        hbBotones.setSpacing(5);
        
        GridPane gpDatosGasto = new GridPane();
        gpDatosGasto.setPadding(new Insets(5, 5, 5, 5));
        gpDatosGasto.setVgap(10);
        gpDatosGasto.setHgap(10);
        gpDatosGasto.add(lbIdGastos, 0, 0);
        gpDatosGasto.add(tfIdGasto, 1, 0);
        gpDatosGasto.add(lbConceptoGasto, 0, 2);
        gpDatosGasto.add(tfConceptoGasto, 1, 2);
        gpDatosGasto.add(hbBotones, 1, 4);
        
        vbPpal.getChildren().addAll(lbTituloVista, hbTipoSeleccion, hbCompSeleccion, tvCatalogoGastos, gpDatosGasto);
        return vbPpal;    
    }
    private VBox ventanaEnviarReporteDiarioGeneral(){
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(5);
        Label lbTituloVista = new Label ("ENVIO DEL REPORTE A SERVIDOR PRINCIPAL GLOBALPLA1.COM");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        ListView lvArchivosRepGen = new ListView();
        lvArchivosRepGen.setMaxSize(350, 800);        
        Label lbListaArchGen = new Label("Lista de archivos Reportes General Diario");
        
        ListView lvArchivosInventario = new ListView();
        lvArchivosInventario.setMaxSize(350, 800);        
        Label lbListaArchInventario = new Label("Lista de archivos Inventario");
        
        ListView lvArchivosCompras = new ListView();
        lvArchivosCompras.setMaxSize(350, 800);        
        Label lbListaArchCompras = new Label("Lista de archivos Compras");
        
        Button btnSalir = new Button("Salir");
        btnSalir.setOnAction((event) -> {
            removerVistas();
        });
        
        Button btnSubirReporteGeneral = new Button ("Subir Reporte General");
        btnSubirReporteGeneral.setOnAction((event) -> {
              String sFTP = "162.241.102.97";
              String sUser = "ctaft@globalpla1.com";
              String sPassword = "Boucarely$2781";
              FTPClient client = new FTPClient();
              try {
                  client.connect(sFTP);
                  boolean login = client.login(sUser,sPassword);
                  if (login){
                      System.out.println("Conexion Establecida..");
                      client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
                      client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
                      client.enterLocalPassiveMode();
                      LocalDateTime lcdt = LocalDateTime.now();
                      String keyDate = String.valueOf(lcdt.getYear())
                              +String.valueOf(lcdt.getMonthValue())
                              +String.valueOf(lcdt.getDayOfMonth())
                              +String.valueOf(lcdt.getHour())
                              +String.valueOf(lcdt.getMinute())
                              +String.valueOf(lcdt.getSecond());
                      lstWhereConcepto.clear();
                      lstWhereConcepto.add("id_suc= 1");
                      List<SUCURSAL> sucList = sucDAO.consultaSucursal(lstWhereConcepto);
                      SUCURSAL sucIdent = sucList.get(0);
                      String filename = "Suc"+sucIdent.getNombre()+"/DiarioGeneral/ReporteDiario_SubidoEl"+keyDate+".pdf";
                      FileInputStream fis = new FileInputStream(lvArchivosRepGen.getSelectionModel().getSelectedItem().toString()); 
                      // Guardando el archivo en el servidor
                      client.storeFile(filename, fis);
                      client.logout();
                      client.disconnect();
                      Alert alMensaje = new Alert(Alert.AlertType.INFORMATION);
                      alMensaje.setContentText("Archivo Enviado con Exito");
                      alMensaje.setTitle("Respuesta");
                      alMensaje.showAndWait();
                  }
              } catch (IOException ioe) {}
        });
        Button btnSubirReporteInventario = new Button ("Subir Reporte Inventario");
        btnSubirReporteInventario.setOnAction((event) -> {
              String sFTP = "162.241.102.97";
              String sUser = "ctaft@globalpla1.com";
              String sPassword = "Boucarely$2781";
              FTPClient client = new FTPClient();
              try {
                  client.connect(sFTP);
                  boolean login = client.login(sUser,sPassword);
                  if (login){
                      System.out.println("Conexion Establecida..");
                      client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
                      client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
                      client.enterLocalPassiveMode();
                      LocalDateTime lcdt = LocalDateTime.now();
                      String keyDate = String.valueOf(lcdt.getYear())
                              +String.valueOf(lcdt.getMonthValue())
                              +String.valueOf(lcdt.getDayOfMonth())
                              +String.valueOf(lcdt.getHour())
                              +String.valueOf(lcdt.getMinute())
                              +String.valueOf(lcdt.getSecond());
                      lstWhereConcepto.clear();
                      lstWhereConcepto.add("id_suc= 1");
                      List<SUCURSAL> sucList = sucDAO.consultaSucursal(lstWhereConcepto);
                      SUCURSAL sucIdent = sucList.get(0);
                      String filename = "Suc"+sucIdent.getNombre()+"/Inventario/ReporteInventario_SubidoEl"+keyDate+".pdf";
                      FileInputStream fis = new FileInputStream(lvArchivosInventario.getSelectionModel().getSelectedItem().toString()); 
                      // Guardando el archivo en el servidor
                      client.storeFile(filename, fis);
                      client.logout();
                      client.disconnect();
                      Alert alMensaje = new Alert(Alert.AlertType.INFORMATION);
                      alMensaje.setContentText("Archivo Enviado con Exito");
                      alMensaje.setTitle("Respuesta");
                      alMensaje.showAndWait();
                  }
              } catch (IOException ioe) {}
        });
        Button btnSubirReporteCompras = new Button ("Subir Reporte Compras");
        btnSubirReporteCompras.setOnAction((event) -> {
              String sFTP = "162.241.102.97";
              String sUser = "ctaft@globalpla1.com";
              String sPassword = "Boucarely$2781";
              FTPClient client = new FTPClient();
              try {
                  client.connect(sFTP);
                  boolean login = client.login(sUser,sPassword);
                  if (login){
                      System.out.println("Conexion Establecida..");
                      client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
                      client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
                      client.enterLocalPassiveMode();
                      LocalDateTime lcdt = LocalDateTime.now();
                      String keyDate = String.valueOf(lcdt.getYear())
                              +String.valueOf(lcdt.getMonthValue())
                              +String.valueOf(lcdt.getDayOfMonth())
                              +String.valueOf(lcdt.getHour())
                              +String.valueOf(lcdt.getMinute())
                              +String.valueOf(lcdt.getSecond());
                      lstWhereConcepto.clear();
                      lstWhereConcepto.add("id_suc= 1");
                      List<SUCURSAL> sucList = sucDAO.consultaSucursal(lstWhereConcepto);
                      SUCURSAL sucIdent = sucList.get(0);
                      String filename = "Suc"+sucIdent.getNombre()+"/Compras/ReporteCompras_SubidoEl"+keyDate+".pdf";
                      FileInputStream fis = new FileInputStream(lvArchivosCompras.getSelectionModel().getSelectedItem().toString()); 
                      // Guardando el archivo en el servidor
                      client.storeFile(filename, fis);
                      client.logout();
                      client.disconnect();
                      Alert alMensaje = new Alert(Alert.AlertType.INFORMATION);
                      alMensaje.setContentText("Archivo Enviado con Exito");
                      alMensaje.setTitle("Respuesta");
                      alMensaje.showAndWait();
                  }
              } catch (IOException ioe) {}
        });
        
        File directoryRepGen = new File("Reportes/DiarioGeneral");
        //File directory = new File("C:\\Users\\i7");
        File[] listaArchivosReporteGen = directoryRepGen.listFiles();
        
        for (File f : listaArchivosReporteGen){
            lvArchivosRepGen.getItems().add(f.getAbsolutePath());
        }
        File directoryInventario = new File("Reportes/Inventario");
        File[] listaArchivosInventario = directoryInventario.listFiles();
        
        for (File fr : listaArchivosInventario){
            lvArchivosInventario.getItems().add(fr.getAbsolutePath());
        }
        
        File directoryCompras = new File("Reportes/Compras");
        File[] listaArchivosCompras = directoryCompras.listFiles();
        
        for (File fr : listaArchivosCompras){
            lvArchivosCompras.getItems().add(fr.getAbsolutePath());
        }
        
        lvArchivosRepGen.getItems().sorted();
        GridPane gpBotonesRepGen = new GridPane();
        gpBotonesRepGen.setVgap(10);
        gpBotonesRepGen.setHgap(10);
        gpBotonesRepGen.add(btnSubirReporteGeneral, 1, 0);
        
        GridPane gpBotonesRepInventario = new GridPane();
        gpBotonesRepInventario.setVgap(10);
        gpBotonesRepInventario.setHgap(10);
        gpBotonesRepInventario.add(btnSubirReporteInventario, 1, 0);
        
        
        GridPane gpBotonesRepCompras = new GridPane();
        gpBotonesRepCompras.setVgap(10);
        gpBotonesRepCompras.setHgap(10);
        gpBotonesRepCompras.add(btnSubirReporteCompras, 1, 0);
        gpBotonesRepCompras.add(btnSalir, 1, 1);

        
        VBox vbRepGeneral = new VBox();
        vbRepGeneral.setSpacing(10);
        VBox vbRepInventario = new VBox();
        vbRepInventario.setSpacing(10);
        
        VBox vbRepCmpras = new VBox();
        vbRepCmpras.setSpacing(10);
        
        vbRepGeneral.getChildren().addAll( lbListaArchGen, lvArchivosRepGen, gpBotonesRepGen);
        vbRepInventario.getChildren().addAll( lbListaArchInventario, lvArchivosInventario, gpBotonesRepInventario);
        vbRepCmpras.getChildren().addAll( lbListaArchCompras, lvArchivosCompras, gpBotonesRepCompras);
        
        HBox hbPpal = new HBox();
        hbPpal.setSpacing(10);
        hbPpal.getChildren().addAll(vbRepGeneral, vbRepInventario, vbRepCmpras);
        
        vbPpal.getChildren().addAll(lbTituloVista, hbPpal);
        return vbPpal;
    }
    private VBox vistaSumarInventarioconArchivo(){
        VBox vbPpal = new VBox();
        vbPpal.setAlignment(Pos.CENTER_LEFT);
        
        ObservableList<detalle_traspaso> lstDetTrasp = FXCollections.observableArrayList();

        Label lbTituloVista = new Label("SUMAR PRODUCTOS DE TRASPASOS A INVENTARIO");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        lbTituloVista.setAlignment(Pos.CENTER_LEFT);

        TableView tvDatos = new TableView();
        tvDatos.setPrefSize(405, 500);

        TableColumn<detalle_traspaso, Integer> columCodigoProd = new TableColumn("CODIGO PROD.");
        columCodigoProd.setMinWidth(100);
        columCodigoProd.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));
        
        TableColumn<detalle_traspaso, String> columDescripcion = new TableColumn("DESCRIPCIÓN");
        columDescripcion.setPrefWidth(200);
        columDescripcion.setCellValueFactory(new PropertyValueFactory<>("descrprod"));
        
        TableColumn<detalle_traspaso, Double> columCantidad = new TableColumn("CANTIDAD");
        columCantidad.setPrefWidth(100);
        columCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        
        tvDatos.getColumns().addAll(columCodigoProd, columDescripcion, columCantidad);
                
        Label lbCargarCat = new Label("Cargar archivo");
        TextField tfCargarCat = new TextField();
        tfCargarCat.setPrefWidth(350);
        
        Button btnBuscar= new Button(" Buscar ");
        btnBuscar.setOnAction((event) -> {
            if (!lstDetTrasp.isEmpty()){lstDetTrasp.clear();}
            
            String archCSV = "";
            File selectedFile;
            FileChooser fcSelecArchCarga = new FileChooser();
            fcSelecArchCarga.setTitle("Archivo origen");
            fcSelecArchCarga.getExtensionFilters().addAll(
               new FileChooser.ExtensionFilter("Archivos CSV", "*.CSV", "*.CSV"),
               new FileChooser.ExtensionFilter("Archivos DATOS", "*.DAT","*.dat"),
               new FileChooser.ExtensionFilter("Archivos Texto", "*.TXT","*.txt"),
               new FileChooser.ExtensionFilter("Todos los archivos", "*.*"));
            selectedFile = fcSelecArchCarga.showOpenDialog(primarioStage);
            tfCargarCat.setText(selectedFile.getAbsolutePath());
            CSVReader csvReader;
            
            try {
                csvReader = new CSVReader(new FileReader(tfCargarCat.getText()));
                String[] fila = null;
                try {
                    int contReg = 1;
                    while((fila = csvReader.readNext()) != null) {
                          detalle_traspaso detTraspT = new detalle_traspaso();
                          detTraspT.setCodigo_prod(Integer.parseInt(fila[0]));
                          detTraspT.setDescrprod(fila[1]);
                          detTraspT.setCantidad(Integer.parseInt(fila[2]));
                          
                          lstDetTrasp.add(detTraspT);
                          tvDatos.setItems(lstDetTrasp);
                          System.out.println(fila[0] + " | " + fila[1] + " | " + fila[2]);
                          
                    }  
                    csvReader.close();
                } catch (IOException ex) {
                    Logger.getLogger(CRMPLAMAR.class.getName()).log(Level.SEVERE, null, ex);
                } catch (CsvValidationException ex) {
                    Logger.getLogger(CRMPLAMAR.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CRMPLAMAR.class.getName()).log(Level.SEVERE, null, ex);
            }                   

        });

        HBox hbTablaDatos = new HBox();
        hbTablaDatos.setAlignment(Pos.CENTER_LEFT);
        hbTablaDatos.getChildren().addAll(tvDatos);
        hbTablaDatos.setMaxWidth(700);

        GridPane gpInput = new GridPane();
        gpInput.setPadding(new Insets(5,5,5,5));
        gpInput.setVgap(5);
        gpInput.setHgap(5);

        gpInput.add(lbCargarCat, 0, 0);
        gpInput.add(tfCargarCat, 0, 1);
        gpInput.add(btnBuscar, 1, 1);
        gpInput.setAlignment(Pos.CENTER_LEFT);
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction((event) -> {
            removerVistas();
        });

        Button btnCargar = new Button(" Sumar Inventario");
        btnCargar.setOnAction((event) -> {
            for(detalle_traspaso detTraspTemp: lstDetTrasp){
                inventario inventTemp = new inventario();
                lstWhereConcepto.clear();
                lstWhereConcepto.add("codigo_prod = "+detTraspTemp.getCodigo_prod());
                inventTemp = invent.consultaInventario(lstWhereConcepto).get(0);
                int existT =inventTemp.getExistencia()+detTraspTemp.getCantidad();
                invent.modificarExistenciaProducto(detTraspTemp.getCodigo_prod(), existT);
            }
            Alert aviso = new Alert(Alert.AlertType.WARNING);
            aviso.setHeaderText(null);
            aviso.setTitle("Informacion de Traspaso");
            aviso.setContentText("Inventario Actualizado");
            Optional<ButtonType> action = aviso.showAndWait();
        });

        HBox hbBotones = new HBox();
        hbBotones.setSpacing(10);
        hbBotones.setAlignment(Pos.CENTER_LEFT);
        hbBotones.getChildren().addAll(btnCargar, btnCancelar);        
        vbPpal.getChildren().addAll(lbTituloVista, gpInput, hbTablaDatos, hbBotones);        
        
        return vbPpal;    
    }
    private VBox vistaRestarInventarioconArchivo(){
        VBox vbPpal = new VBox();
        vbPpal.setAlignment(Pos.CENTER_LEFT);
        
        ObservableList<detalle_traspaso> lstDetTrasp = FXCollections.observableArrayList();

        Label lbTituloVista = new Label("RESTAR PRODUCTOS DE TRASPASOS A INVENTARIO");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        lbTituloVista.setAlignment(Pos.CENTER_LEFT);

        TableView tvDatos = new TableView();
        tvDatos.setPrefSize(405, 500);

        TableColumn<detalle_traspaso, Integer> columCodigoProd = new TableColumn("CODIGO PROD.");
        columCodigoProd.setMinWidth(100);
        columCodigoProd.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));
        
        TableColumn<detalle_traspaso, String> columDescripcion = new TableColumn("DESCRIPCIÓN");
        columDescripcion.setPrefWidth(200);
        columDescripcion.setCellValueFactory(new PropertyValueFactory<>("descrprod"));
        
        TableColumn<detalle_traspaso, Double> columCantidad = new TableColumn("CANTIDAD");
        columCantidad.setPrefWidth(100);
        columCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        
        tvDatos.getColumns().addAll(columCodigoProd, columDescripcion, columCantidad);
                
        Label lbCargarCat = new Label("Cargar archivo");
        TextField tfCargarCat = new TextField();
        tfCargarCat.setPrefWidth(350);
        
        Button btnBuscar= new Button(" Buscar ");
        btnBuscar.setOnAction((event) -> {
            if (!lstDetTrasp.isEmpty()){lstDetTrasp.clear();}
            
            String archCSV = "";
            File selectedFile;
            FileChooser fcSelecArchCarga = new FileChooser();
            fcSelecArchCarga.setTitle("Archivo origen");
            fcSelecArchCarga.getExtensionFilters().addAll(
               new FileChooser.ExtensionFilter("Archivos CSV", "*.CSV", "*.CSV"),
               new FileChooser.ExtensionFilter("Archivos DATOS", "*.DAT","*.dat"),
               new FileChooser.ExtensionFilter("Archivos Texto", "*.TXT","*.txt"),
               new FileChooser.ExtensionFilter("Todos los archivos", "*.*"));
            selectedFile = fcSelecArchCarga.showOpenDialog(primarioStage);
            tfCargarCat.setText(selectedFile.getAbsolutePath());
            CSVReader csvReader;
            
            try {
                csvReader = new CSVReader(new FileReader(tfCargarCat.getText()));
                String[] fila = null;
                try {
                    int contReg = 1;
                    while((fila = csvReader.readNext()) != null) {
                          detalle_traspaso detTraspT = new detalle_traspaso();
                          detTraspT.setCodigo_prod(Integer.parseInt(fila[0]));
                          detTraspT.setDescrprod(fila[1]);
                          detTraspT.setCantidad(Integer.parseInt(fila[2]));
                          
                          lstDetTrasp.add(detTraspT);
                          tvDatos.setItems(lstDetTrasp);
                          System.out.println(fila[0] + " | " + fila[1] + " | " + fila[2]);
                          
                    }  
                    csvReader.close();
                } catch (IOException ex) {
                    Logger.getLogger(CRMPLAMAR.class.getName()).log(Level.SEVERE, null, ex);
                } catch (CsvValidationException ex) {
                    Logger.getLogger(CRMPLAMAR.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CRMPLAMAR.class.getName()).log(Level.SEVERE, null, ex);
            }                   

        });

        HBox hbTablaDatos = new HBox();
        hbTablaDatos.setAlignment(Pos.CENTER_LEFT);
        hbTablaDatos.getChildren().addAll(tvDatos);
        hbTablaDatos.setMaxWidth(700);

        GridPane gpInput = new GridPane();
        gpInput.setPadding(new Insets(5,5,5,5));
        gpInput.setVgap(5);
        gpInput.setHgap(5);

        gpInput.add(lbCargarCat, 0, 0);
        gpInput.add(tfCargarCat, 0, 1);
        gpInput.add(btnBuscar, 1, 1);
        gpInput.setAlignment(Pos.CENTER_LEFT);
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction((event) -> {
            removerVistas();
        });

        Button btnCargar = new Button(" Restar Inventario");
        btnCargar.setOnAction((event) -> {
            for(detalle_traspaso detTraspTemp: lstDetTrasp){
                inventario inventTemp = new inventario();
                lstWhereConcepto.clear();
                lstWhereConcepto.add("codigo_prod = "+detTraspTemp.getCodigo_prod());
                inventTemp = invent.consultaInventario(lstWhereConcepto).get(0);
                int existT =inventTemp.getExistencia()-detTraspTemp.getCantidad();
                invent.modificarExistenciaProducto(detTraspTemp.getCodigo_prod(), existT);
            }
            Alert aviso = new Alert(Alert.AlertType.WARNING);
            aviso.setHeaderText(null);
            aviso.setTitle("Informacion de Traspaso");
            aviso.setContentText("Inventario Actualizado");
            Optional<ButtonType> action = aviso.showAndWait();
        });

        HBox hbBotones = new HBox();
        hbBotones.setSpacing(10);
        hbBotones.setAlignment(Pos.CENTER_LEFT);
        hbBotones.getChildren().addAll(btnCargar, btnCancelar);        
        vbPpal.getChildren().addAll(lbTituloVista, gpInput, hbTablaDatos, hbBotones);        
        
        return vbPpal;    
    }
    private VBox vistaConsultarTraspasos(){
        Label lbTituloVista = new Label("CONSULTAR TRASPASOS");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        if (lstTrasp.size()>0){
            //lstCompra = FXCollections.observableArrayList();
            lstTrasp.clear();
        }
        if (!lstdetrasp.isEmpty()){lstdetrasp.clear();}
        
        VBox vbVistaPpal = new VBox();
        
        Label lbFechaTraspaso = new Label("Fecha Traspaso: ");
        Label lbCantidad  = new Label("Cantidad: ");
        Label lbCodigoProd  = new Label("Codigo Producto: ");
        Label lbDescProducto = new Label ("Descripción Producto: ");
        
        
        DatePicker dpFecha = new DatePicker(LocalDate.now());
        TextField tfCantidad = new TextField();
        tfCantidad.setMaxWidth(80);
        TextField tfCodigoProducto = new TextField();
        TextField tfDescrProd = new TextField();
        tfDescrProd.setMaxWidth(300);
        tfDescrProd.setPrefWidth(300);
        
        //Componentes de Interfaz
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgBusquedas = new ToggleGroup();
        
        RadioButton rbTodos = new RadioButton("Todos");
        RadioButton rbFecha = new RadioButton("Fecha Traspaso");
        rbFecha.setSelected(true);
        
        rbTodos.setToggleGroup(tgBusquedas);
        rbFecha.setToggleGroup(tgBusquedas);
               
        TextField tfCodigo = new TextField();
        Label lbFecha = new Label("Fecha Traspaso:");
        DatePicker dpFechaTraspaso = new DatePicker(LocalDate.now());
        
        Label lbTraspasos = new Label("Tabla Traspasos: ");
        TableView tvTraspasos = new TableView();
        tvTraspasos.setPrefHeight(350);
        tvTraspasos.setPrefWidth(550);
          //id_traspaso, fecha, tienda_traspaso, responsable_tienda_envia,
          //responsable_tienda_recibe, responsable_transportar_prod
          
        
        TableColumn<traspaso, Integer> idTraspasoColumna = new TableColumn<>("Id Traspaso");
        idTraspasoColumna.setMinWidth(120);
        idTraspasoColumna.setCellValueFactory(new PropertyValueFactory<>("id_traspaso"));

        TableColumn<traspaso, String> fechaColumna = new TableColumn<>("Fecha");
        fechaColumna.setMinWidth(120);
        fechaColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        
        TableColumn<traspaso, String> tiendaTraspColumna = new TableColumn<>("Tienda Traspaso");
        tiendaTraspColumna.setMinWidth(120);
        tiendaTraspColumna.setCellValueFactory(new PropertyValueFactory<>("tienda_traspaso"));
        
        TableColumn<traspaso, String> respTiendaEnvColumna = new TableColumn<>("Resp. Tienda Envia");
        respTiendaEnvColumna.setMinWidth(120);
        respTiendaEnvColumna.setCellValueFactory(new PropertyValueFactory<>("responsable_tienda_envia")); 
        
        TableColumn<traspaso, String> respTiendaRecColumna = new TableColumn<>("Resp. Tienda Recibe");
        respTiendaRecColumna.setMinWidth(120);
        respTiendaRecColumna.setCellValueFactory(new PropertyValueFactory<>("responsable_tienda_recibe")); 
        
        TableColumn<traspaso, String> respTransportaProdColumna = new TableColumn<>("Resp. Trasp. Producto");
        respTransportaProdColumna.setMinWidth(120);
        respTransportaProdColumna.setCellValueFactory(new PropertyValueFactory<>("responsable_transportar_prod")); 
        
        tvTraspasos.getColumns().addAll(idTraspasoColumna, fechaColumna, tiendaTraspColumna,
                respTiendaEnvColumna, respTiendaRecColumna, respTransportaProdColumna);
        
        MenuItem miEliminarTraspaso = new MenuItem("Eliminar Traspaso");
        miEliminarTraspaso.setOnAction((event) -> {
            traspaso taspTemp = (traspaso) tvTraspasos.getSelectionModel().getSelectedItem();
            for (detalle_traspaso det: lstdetrasp){
                detraspDAO.borrarDetTraspaso(det.getId_detalle_traspaso());
            }
            traspDAO.borrarTraspaso(taspTemp.getId_traspaso());
            lstdetrasp.clear();
            tvTraspasos.getItems().remove(tvTraspasos.getSelectionModel().getSelectedIndex());
        });
        
        ContextMenu cmMenuTrasp = new ContextMenu(miEliminarTraspaso);
        tvTraspasos.setContextMenu(cmMenuTrasp);
        
        //LocalDate ldToday = dpFechaCompra.getValue();

        List<String> lstWhere = new ArrayList<>();
        lstWhere.clear();
        lstWhere.add("fecha = '"+dpFechaTraspaso.getValue().toString()+"' ");
        //lstWhere.add("id_compra is not null");
        lstTrasp = FXCollections.observableArrayList(traspDAO.consultaTraspaso(lstWhere));
        tvTraspasos.setItems(lstTrasp);
        
        Label lbProducto = new Label("Tabla Productos Traspasados: ");
        TableView tvProductosSelecc = new TableView();
        tvProductosSelecc.setPrefHeight(350);
        tvProductosSelecc.setPrefWidth(550);
        
        TableColumn<detalle_traspaso, Integer> codigoProCompColumna = new TableColumn<>("Codigo Producto");
        codigoProCompColumna.setMinWidth(80);
        codigoProCompColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));
        
        TableColumn<detalle_traspaso, String> descrProCompColumna = new TableColumn<>("Descripción");
        descrProCompColumna.setMinWidth(220);
        descrProCompColumna.setCellValueFactory(new PropertyValueFactory<>("descrprod"));
        
        TableColumn<detalle_traspaso, Integer> cantidadProCompColumna = new TableColumn<>("Cantidad");
        cantidadProCompColumna.setMinWidth(80);
        cantidadProCompColumna.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        
            
        tvProductosSelecc.getColumns().addAll(codigoProCompColumna, descrProCompColumna, cantidadProCompColumna);
        tvProductosSelecc.setItems(lstdetrasp);

        tvTraspasos.setOnMouseClicked((event) -> {
           if (tvTraspasos.getItems().size()>0){
             traspaso traspT = new traspaso();
             traspT = (traspaso) tvTraspasos.getSelectionModel().getSelectedItem();
             lstWhere.clear();
             lstWhere.add("id_traspaso = "+traspT.getId_traspaso());
             lstdetrasp = FXCollections.observableArrayList(detraspDAO.consultaDetTraspaso(lstWhere));
             for (detalle_traspaso detT: lstdetrasp){
                 lstWhere.add("codigo_prod = "+detT.getCodigo_prod());
                 detT.setDescrprod(invent.consultaInventario(lstWhere).get(0).getDescripcion());
             }
             tvProductosSelecc.setItems(lstdetrasp);
           }
        });
        
        tvProductosSelecc.setOnMouseClicked((event) -> {
            detalle_traspaso detTraspT = (detalle_traspaso) tvProductosSelecc.getSelectionModel().getSelectedItem();
            tfCantidad.setText(String.valueOf(detTraspT.getCantidad()));
            tfCodigoProducto.setText(String.valueOf(detTraspT.getCodigo_prod()));
            tfDescrProd.setText(detTraspT.getDescrprod());
        });
        
        VBox vbHead = new VBox();

        HBox hbTipoSeleccion = new HBox();
        hbTipoSeleccion.getChildren().addAll(rbTodos, rbFecha);
        hbTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        hbTipoSeleccion.setSpacing(5);
              
        VBox vbFechaSelec = new VBox();
        vbFechaSelec.getChildren().addAll(lbFecha, dpFechaTraspaso);
        vbFechaSelec.setSpacing(5);
        
        HBox hbCompSeleccion = new HBox();
        Button btnBuscarProductos = new Button("Seleccionar");
        btnBuscarProductos.setMaxHeight(50);
        btnBuscarProductos.setOnAction((ActionEvent e)->{
         List<inventario> lstInv = new ArrayList<>();
         List<String> lstWherelc = new ArrayList<>();
         
          if (rbTodos.isSelected()){
           lstWhere.clear();
           lstWhere.add("id_traspaso is not null");
           lstTrasp = FXCollections.observableArrayList(traspDAO.consultaTraspaso(lstWhere));
           tvTraspasos.setItems(lstTrasp);
         } 
         
         if (rbFecha.isSelected()){
           lstWhere.clear();
           lstWhere.add("fecha = '"+dpFechaTraspaso.getValue().toString()+"' ");
           lstTrasp = FXCollections.observableArrayList(traspDAO.consultaTraspaso(lstWhere));
           tvTraspasos.setItems(lstTrasp);
         }
         
        });
        
        hbCompSeleccion.getChildren().addAll(vbFechaSelec, btnBuscarProductos);
        hbCompSeleccion.setPadding(new Insets(10, 10, 10, 10));
        hbCompSeleccion.setSpacing(5);

        Separator spSeleccionProductos = new Separator();
        vbHead.getChildren().addAll(lbTipoBusqueda, hbTipoSeleccion, hbCompSeleccion, spSeleccionProductos);
        
        VBox vbTabCompra = new VBox();
        vbTabCompra.setPadding(new Insets(5, 5, 5, 5));
        
        vbTabCompra.getChildren().addAll(lbTraspasos, tvTraspasos, lbProducto, tvProductosSelecc);
        
        GridPane gpBloqueProducto = new GridPane();
        gpBloqueProducto.setPadding(new Insets(5, 5, 5, 5));
        gpBloqueProducto.setVgap(10);
        gpBloqueProducto.setHgap(10);
        
        gpBloqueProducto.add(lbFechaTraspaso , 0, 0);
        gpBloqueProducto.add(dpFecha , 1, 0);
             
        gpBloqueProducto.add(lbCodigoProd , 0, 1);
        gpBloqueProducto.add(tfCodigoProducto , 1, 1);
        
        gpBloqueProducto.add(lbDescProducto , 2, 1);
        gpBloqueProducto.add(tfDescrProd , 3, 1);
        
        gpBloqueProducto.add(lbCantidad , 0, 2);
        gpBloqueProducto.add(tfCantidad , 1, 2);
        
        Button btnSalir = new Button("Salir");
        btnSalir.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        
        HBox hbBotonesInferiores = new HBox();
        hbBotonesInferiores.setAlignment(Pos.CENTER_RIGHT);
        hbBotonesInferiores.getChildren().addAll(btnSalir);
        
        VBox vbTabProducto = new VBox();
        vbTabProducto.setSpacing(5);
        vbTabProducto.getChildren().addAll(gpBloqueProducto, hbBotonesInferiores);
        
        HBox hbBody = new HBox();
        hbBody.getChildren().addAll(vbTabCompra, vbTabProducto);
        
        vbVistaPpal.getChildren().addAll(lbTituloVista, vbHead,hbBody);
        
        return vbVistaPpal; 
        
    }
    private VBox vistaConsultarBitacoraPrecios(){
        Label lbTituloVista = new Label("CONSULTAR BITACORA PRECIOS");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        if (lstBitacoraPrecio.size()>0){
            //lstCompra = FXCollections.observableArrayList();
            lstBitacoraPrecio.clear();
        }
        
        VBox vbVistaPpal = new VBox();
        
        
        //Componentes de Interfaz
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgBusquedas = new ToggleGroup();
        
        RadioButton rbTodos = new RadioButton("Todos");
        RadioButton rbFecha = new RadioButton("Fechas ");
        rbFecha.setSelected(true);
        
        rbTodos.setToggleGroup(tgBusquedas);
        rbFecha.setToggleGroup(tgBusquedas);
               
        TextField tfCodigo = new TextField();
        Label lbFecha = new Label("Fecha :");
        DatePicker dpFechasCambioPrecios = new DatePicker(LocalDate.now());
        
        Label lbTablaBitacoraPrecios = new Label("Tabla Bitacora Precios: ");
        TableView tvBitacoraPrecios = new TableView();
        tvBitacoraPrecios.setPrefHeight(350);
        tvBitacoraPrecios.setPrefWidth(1040);
          //id_traspaso, fecha, tienda_traspaso, responsable_tienda_envia,
          //responsable_tienda_recibe, responsable_transportar_prod
          
        
        TableColumn<bitacora_precio, Integer> idColumna = new TableColumn<>("Id ");
        idColumna.setMinWidth(80);
        idColumna.setCellValueFactory(new PropertyValueFactory<>("id_bitacora"));

        TableColumn<bitacora_precio, String> fechaColumna = new TableColumn<>("Fecha");
        fechaColumna.setMinWidth(120);
        fechaColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        
        TableColumn<bitacora_precio, Float> preMenudColumna = new TableColumn<>("Precio Menudeo");
        preMenudColumna.setMinWidth(120);
        preMenudColumna.setCellValueFactory(new PropertyValueFactory<>("precio_menudeo"));
        
        TableColumn<bitacora_precio, Float> preMayorColumna = new TableColumn<>("Precio Mayoreo");
        preMayorColumna.setMinWidth(120);
        preMayorColumna.setCellValueFactory(new PropertyValueFactory<>("precio_mayoreo"));
        
        TableColumn<bitacora_precio, Float> preMenudAntColumna = new TableColumn<>("Precio Menudeo Ant.");
        preMenudAntColumna.setMinWidth(120);
        preMenudAntColumna.setCellValueFactory(new PropertyValueFactory<>("precio_menudeo_ant"));
        
        TableColumn<bitacora_precio, Float> preMayorAntColumna = new TableColumn<>("Precio Mayoreo Ant.");
        preMayorAntColumna.setMinWidth(120);
        preMayorAntColumna.setCellValueFactory(new PropertyValueFactory<>("precio_mayoreo_ant")); 
        
        TableColumn<bitacora_precio, Integer> idUserColumna = new TableColumn<>("Id Usuario");
        idUserColumna.setMinWidth(120);
        idUserColumna.setCellValueFactory(new PropertyValueFactory<>("id_usuario")); 
        
        TableColumn<bitacora_precio, String> userColumna = new TableColumn<>("Usuario");
        userColumna.setMinWidth(120);
        userColumna.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));
        
        TableColumn<bitacora_precio, String> codigoProdColumna = new TableColumn<>("Codigo Producto");
        codigoProdColumna.setMinWidth(120);
        codigoProdColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod")); 
        
        tvBitacoraPrecios.getColumns().addAll(idColumna, fechaColumna, preMenudColumna, preMayorColumna,
                preMenudAntColumna, preMayorAntColumna, idUserColumna, userColumna, codigoProdColumna);

        List<String> lstWhere = new ArrayList<>();
        lstWhere.clear();
        lstWhere.add("fecha LIKE '%"+dpFechasCambioPrecios.getValue().toString()+"%' ");
        lstBitacoraPrecio = FXCollections.observableArrayList(bitacoraDAO.consultaBitacora(lstWhere));
        tvBitacoraPrecios.setItems(lstBitacoraPrecio);

        
        VBox vbHead = new VBox();

        HBox hbTipoSeleccion = new HBox();
        hbTipoSeleccion.getChildren().addAll(rbTodos, rbFecha);
        hbTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        hbTipoSeleccion.setSpacing(5);
              
        VBox vbFechaSelec = new VBox();
        vbFechaSelec.getChildren().addAll(lbFecha, dpFechasCambioPrecios);
        vbFechaSelec.setSpacing(5);
        
        HBox hbCompSeleccion = new HBox();
        Button btnBuscarProductos = new Button("Seleccionar");
        btnBuscarProductos.setMaxHeight(50);
        btnBuscarProductos.setOnAction((ActionEvent e)->{
         List<inventario> lstInv = new ArrayList<>();
         List<String> lstWherelc = new ArrayList<>();
         
          if (rbTodos.isSelected()){
           lstWhere.clear();
           lstWhere.add("id_bitacora is not null");
           lstBitacoraPrecio = FXCollections.observableArrayList(bitacoraDAO.consultaBitacora(lstWhere));
           tvBitacoraPrecios.setItems(lstBitacoraPrecio);
         } 
         
         if (rbFecha.isSelected()){
           lstWhere.clear();
           lstWhere.add("fecha LIKE '%"+dpFechasCambioPrecios.getValue().toString()+"%' ");
           lstBitacoraPrecio = FXCollections.observableArrayList(bitacoraDAO.consultaBitacora(lstWhere));
           tvBitacoraPrecios.setItems(lstBitacoraPrecio);
         }
         
        });
        
        hbCompSeleccion.getChildren().addAll(vbFechaSelec, btnBuscarProductos);
        hbCompSeleccion.setPadding(new Insets(10, 10, 10, 10));
        hbCompSeleccion.setSpacing(5);

        Separator spSeleccionProductos = new Separator();
        vbHead.getChildren().addAll(lbTipoBusqueda, hbTipoSeleccion, hbCompSeleccion, spSeleccionProductos);
        
        VBox vbTabBitacora = new VBox();
        vbTabBitacora.setPadding(new Insets(5, 5, 5, 5));
        
        vbTabBitacora.getChildren().addAll(lbTablaBitacoraPrecios, tvBitacoraPrecios);
        
           
        Button btnSalir = new Button("Salir");
        btnSalir.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        
        HBox hbBotonesInferiores = new HBox();
        hbBotonesInferiores.setAlignment(Pos.CENTER_RIGHT);
        hbBotonesInferiores.getChildren().addAll(btnSalir);
               
        HBox hbBody = new HBox();
        hbBody.getChildren().addAll(vbTabBitacora);
        
        vbVistaPpal.getChildren().addAll(lbTituloVista, vbHead,hbBody, hbBotonesInferiores);
        
        return vbVistaPpal; 
        
    }
    
    public void loginEmpresa(){
        Stage loginStage = new Stage();
        loginStage.setTitle("TIENDAS PLAMAR LOGIN");
        
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        
        Label lbTituloVista = new Label("ACCESO");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        Label lbUsuario = new Label("Usuario:");
        Label lbContrasena = new Label("Contraseña:");        
        TextField tfUsuario = new TextField();
        tfUsuario.setText("");
        tfUsuario.setPrefWidth(180);
        tfUsuario.setAlignment(Pos.CENTER);
        PasswordField tfContrasena = new PasswordField();
        tfContrasena.setPrefWidth(180);
        tfContrasena.setAlignment(Pos.CENTER);        
        tfContrasena.setPromptText("********");
        tfContrasena.setText("");
        
        GridPane gpUsuario = new GridPane();
        gpUsuario.setPadding(new Insets(5, 5, 5, 5));
        gpUsuario.setVgap(10);
        gpUsuario.setHgap(10);
        gpUsuario.setAlignment(Pos.CENTER);
        
        gpUsuario.add(lbUsuario,0,1);
        gpUsuario.add(tfUsuario,1,1);
        gpUsuario.add(lbContrasena, 0, 2);
        gpUsuario.add(tfContrasena, 1, 2);
        
        Button btnIngresar = new Button("Ingresar");
        btnIngresar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
            Alert aviso = new Alert(Alert.AlertType.WARNING);
            if (tfUsuario.getText().isEmpty() || tfContrasena.getText().isEmpty()){
                aviso.setContentText("Campo(s) vacío(s)");
                Optional<ButtonType> action = aviso.showAndWait();
                Boolean vUsuarioCorrecto = false;
            }
            else{
                usuario uslogin = new usuario();
                lstWhereConcepto.clear();
                lstWhereConcepto.add("usuario = '"+tfUsuario.getText()+"' ");
                uslogin = userDAO.consultaUsuario(lstWhereConcepto).get(0);
                
                if (tfContrasena.getText().equals(uslogin.getClave())){
                        aviso.setContentText("Acceso Permitido");
                        //Optional<ButtonType> action = aviso.showAndWait();
                        if (uslogin.getTipo().toString().equals("VENTA")){
                            //miEliminarVentas.setDisable(true);
                            miNuevoCliente.setDisable(true);
                            miModificarCliente.setDisable(true);
                            miEliminarCliente.setDisable(true);
                            mInventario.setDisable(true);
                            mProveedores.setDisable(true);
                            mAdmin.setDisable(true);
                            mNotas.setDisable(true);
                        
                        }else if (uslogin.getTipo().toString().equals("INVENTARIO")){
                            mAdmin.setDisable(true);
                        }
                        UsuarioActivo = uslogin.getNombre_completo();
                        usrActivo = uslogin;
                        Label lbUsuarioActivo = new Label(UsuarioActivo);
                        hbBarraEstado.setAlignment(Pos.CENTER_RIGHT);
                        hbBarraEstado.getChildren().add(lbUsuarioActivo);
                        loginStage.close();
                        primarioStage.show();
                }
                else{
                    aviso.setContentText("Usuario y/o Contraseña Incorrectos");
                    Optional<ButtonType> action = aviso.showAndWait();
                }
            }
            }
        });
        
        Button btnSalir = new Button("   Salir   ");
        btnSalir.setOnAction((ActionEvent e)->{
            loginStage.close();
        });
        
        HBox hbBotones = new HBox();
        hbBotones.setSpacing(10);
        hbBotones.getChildren().addAll(btnIngresar, btnSalir);
        hbBotones.setAlignment(Pos.CENTER);
        
        vbPpal.getChildren().addAll(lbTituloVista, gpUsuario, hbBotones);
        
        StackPane rootSelectClientes = new StackPane();
        rootSelectClientes.getChildren().addAll(vbPpal);
        Scene scene = new Scene(rootSelectClientes,300,200);
        loginStage.setScene(scene);
        loginStage.setIconified(false);
        loginStage.initModality(Modality.WINDOW_MODAL);
        loginStage.show();
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
