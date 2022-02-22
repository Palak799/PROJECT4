package in.co.sunrays.ctl;

import java.io.IOException;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.sunrays.bean.BaseBean;
import in.co.sunrays.bean.MarksheetBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.model.MarksheetModel;
import in.co.sunrays.util.DataUtility;
import in.co.sunrays.util.DataValidator;
import in.co.sunrays.util.PropertyReader;
import in.co.sunrays.util.ServletUtility;
/**
 * get marksheet functionality ctl.to perform  get marksheet opeation
 * @author priyal
 *
 */
@ WebServlet(name="GetMarksheetCtl",urlPatterns={"/ctl/GetMarksheetCtl"})
public class GetMarksheetCtl extends BaseCtl {
	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("validate method of getMarksheetCtl");
	//	log.debug("GetMarksheetCtl method validate Started");
		
		boolean pass=true;
		
		if(DataValidator.isNull(request.getParameter("rollNo"))){
			request.setAttribute("rollNo",PropertyReader.getValue("error.require", "Roll Number"));
			pass=false;
		}
		
		//log.debug("GetMarksheetCtl method validate End");
		
		return pass;
	}


	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		System.out.println("populate method of getMarksheetCtl");
	//	log.debug("GetMarksheetCtl method populatedBean Started");
		
		MarksheetBean bean=new MarksheetBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));
		bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
		bean.setMaths(DataUtility.getInt(request.getParameter("maths")));
		
	//	log.debug("GetMarksheetCtl method populatedbean End");
		return bean;
	}

	/**
	 * Concept of Display method
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("doget method of getMarksheetCtl");
		//log.debug("GetMarksheetCtl method doGet Started");
		
		ServletUtility.forward(getView(), request, response);
		
	}
	
	/**
     * Concept of submit method
     *
     */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("doPost method of getMarksheetCtl");
		//log.debug("GetMarksheetCtl method doPost Started");
		
		String op=DataUtility.getString(request.getParameter("operation"));
		
		MarksheetModel model=new MarksheetModel();
		MarksheetBean bean=(MarksheetBean) populateBean(request);
//		long id=DataUtility.getLong(request.getParameter("id"));
		
		if(OP_GO.equalsIgnoreCase(op)){
			
			try{
				bean=model.findByRollNo(bean.getRollNo());
				if(bean!=null){
					ServletUtility.setBean(bean, request);
				}else{
					ServletUtility.setErrorMessage("RollNo Does Not exist", request);
					
				}
			}catch(ApplicationException e){
				e.printStackTrace();
			//	log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		//log.debug("MarksheetCtl Method doGet End");
	
	}


	@Override
	protected String getView() {
		
		// TODO Auto-generated method stub
		return ORSView.GET_MARKSHEET_VIEW;
	}

}
