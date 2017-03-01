package FileManager;


public class MessageInterpreter {
    
    public MessageInterpreter()
    {
        // TO DO: instanciar la clase de MAU
    }
    
    public static String mensaje( String descripcionMensaje )
    {
        String tipoMensaje = "";
        // TO DO: modificar mensajes
        switch ( descripcionMensaje ) {
            case "El programa ha sido simulado con éxito": tipoMensaje = "Execution";
                break;
            case "Direccionamiento resulta en desalineamiento": tipoMensaje = "Error";
                break;
            case "Dirección prohibida": tipoMensaje = "Error";
                break;
            case "Intrucción no soportada": tipoMensaje = "Error";
                break;
            case "El archivo que se desea simular es muy grande para la memoria disponible"
                    + " o existe una instrucción que viola el formato establecido": tipoMensaje = "Error";
                break;
            default: tipoMensaje = "Error";
                break;
      }
        return "ISIv1@" + tipoMensaje + ":-~$" + " " + descripcionMensaje + "\n";
    }
    
    public static String mensajeAssembly(String message)
    {
        return "ISIv1@" + "Assembly" + ":-~$" + " " + message + "\n";
    }
    
    public static String mensajeExecution(String message)
    {
        return "ISIv1@" + "Execution" + ":-~$" + " " + message + "\n";
    }
    
    public static String showActualPath(String msj)
    {
        return "ISIv1@" + "CurrentFile" + ":-~$" + " " + msj+ "\n";
    }
    
    public static String initMensaje(String msj)
    {
        return "ISIv1@" + "Info" + ":-~$" + " " + msj+ "\n";
    }
    
    public static String aboutMessage()
    {
        String msj = "";
        return msj;
    }
    
}
