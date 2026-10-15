package webservices;

import entities.UniteEnseignement;
import metiers.ModuleBusiness;
import entities.Module;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.List;

@Path("/mod")
public class ModuleRestApi {

    public static ModuleBusiness modhelper = new ModuleBusiness();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addmodule(Module module){
        if (module.getUniteEnseignement()!=null && modhelper.addModule(module)){
            return Response.status(201).entity(module).build();
        }else{
            return Response.status(404).build();
        }
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getallModules(){
        return Response.status(200).entity(modhelper.getAllModules()).build();
    }

    @Path("/{matricule}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModulesbyMatric(@PathParam(value = "matricule")String matricule){
        Module module=modhelper.getModuleByMatricule(matricule);
        if (module!=null){
            return Response.status(200).entity(module).build();
        }else {
            return Response.status(404).build();
        }
    }
    @Path("/{matricule}")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteModule (@PathParam(value = "matricule")String matricule){
        if (modhelper.deleteModule(matricule)){
            return Response.status(200).entity("module was deleted successfully").build();
        }else {
            return Response.status(404).entity("the module does not exist").build();
        }
    }
    @PUT
    @Path("/{matricule}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response editModule (@PathParam(value = "matricule")String matricule,
                                Module module){
        if (modhelper.updateModule(matricule,module)){
            return Response.status(200).entity(module).entity("modification success").build();
        }else{
            return Response.status(404).entity("module does not exist").build();
        }
    }
    @GET
    @Path("/ue")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModulebyUE (@QueryParam(value = "codeUE")int codeUE){
        UniteEnseignement ue =new UniteEnseignement();
        ue.setCode(codeUE);
        List<Module> result=modhelper.getModulesByUE(ue);
        if (result.isEmpty()){
            return Response.status(404).build();
        }else {
            return Response.status(200).entity(result).build();
        }
    }

}
