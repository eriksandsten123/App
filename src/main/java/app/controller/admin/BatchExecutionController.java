package app.controller.admin;

import app.batch.BatchExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;

@Controller
public class BatchExecutionController {
    private static final String JOB_NAME_PREFIX = "BatchExecutor";

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/admin/batch/{jobName}")
    public void getOrder(@PathVariable String jobName, final HttpServletResponse response) throws Exception {
        final BatchExecutor executor = getExecutorJobByName(jobName);

        executor.executeBatch();

        response.setContentType("text/plain");
        response.getWriter().print("Batch job " + jobName + " done!");
    }

    private BatchExecutor getExecutorJobByName(final String jobName) {
        return applicationContext.getBean(jobName + JOB_NAME_PREFIX, BatchExecutor.class);
    }
}
