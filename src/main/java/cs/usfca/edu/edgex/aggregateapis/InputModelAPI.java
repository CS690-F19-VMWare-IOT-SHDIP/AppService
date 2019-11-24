package cs.usfca.edu.edgex.aggregateapis;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import cs.usfca.edu.edgex.apis.flowapis.FlowHandlers;
import cs.usfca.edu.edgex.exceptions.EventNotFoundException;
import cs.usfca.edu.edgex.exceptions.FlowNotFoundException;
import cs.usfca.edu.edgex.exceptions.InvalidInputException;
import cs.usfca.edu.edgex.model.FlowIdModel;
import cs.usfca.edu.edgex.model.FlowModel;
import cs.usfca.edu.edgex.utils.ApiInputMode;

@Controller
@RequestMapping("/aggregate")
public class InputModelAPI {
	
	/**
	 * Reads the JSON input and creates devices, events and a Flow
	 * @param inputModel
	 * @return
	 */
	@PostMapping(value = "/add", consumes = "application/json")
	@ResponseBody()
	public ResponseEntity<?> registerFlow(@RequestBody InputModel inputModel) {
		try {
			String resp = InputModelHandlers.processInput(inputModel);
			FlowIdModel fidM = new FlowIdModel();
			fidM.setFlowId(resp);
			return ResponseEntity.status(HttpStatus.OK).body(fidM);
		}
		catch(EventNotFoundException | FlowNotFoundException ef) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, ef.getMessage(), null);
		}
		catch(InvalidInputException ief) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ief.getMessage(), null);
		}
	}
	
}
