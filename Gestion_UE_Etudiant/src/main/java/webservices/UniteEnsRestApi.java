package webservices;


import entities.UniteEnseignement;
import metiers.UniteEnseignementBusiness;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ue")
public class UniteEnsRestApi {

    private static UniteEnseignementBusiness uehelper = new UniteEnseignementBusiness();

    @Path("/add")
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addUE(UniteEnseignement ue){
        if (uehelper.addUniteEnseignement(ue)){
            return Response.status(201).entity("success").build();
        }else{
            return Response.status(404).entity("failed").build();
        }
    }

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getlistUe(@QueryParam(value = "semester") Integer semester,
                              @QueryParam(value = "code") Integer code){

        if (code != null ) {
            UniteEnseignement ue = uehelper.getUEByCode(code);
            if (ue != null) {
                return Response.status(200).entity(ue).build();
            } else {
                return Response.status(404).entity("code does not exist").build();
            }
        }
            if (semester!=null){
                return Response.status(200).entity(uehelper.getUEBySemestre(semester)).build();
            }

        return Response.status(200).entity(uehelper.getListeUE()).build();
    }
    @Path("/{code}")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteue(@PathParam(value = "code")int code){
        if (uehelper.deleteUniteEnseignement(code)){
            return Response.status(200).entity("deletion was successful").build();
        }
        else {
            return Response.status(404).entity("professer was not found").build();
        }
    }
    @Path("/{code}")
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.TEXT_PLAIN)
    public Response modifyue(@PathParam(value = "code")int code,
                             UniteEnseignement ue){
        if (uehelper.updateUniteEnseignement(code,ue)){
            return Response.status(200).entity("modification success").build();
        }else{
            return Response.status(404).entity("code does not exist").build();
        }
    }

}
