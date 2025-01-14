package oncall.view;

import java.util.List;
import oncall.view.output.ConsoleWriter;
import oncall.view.output.Writer;

public class OutputView {

    private static final Writer DEFAULT_WRITER = new ConsoleWriter();

    private final Writer writer;

    public OutputView() {
        this(DEFAULT_WRITER);
    }

    public OutputView(Writer writer) {
        this.writer = writer;
    }

    public void printResults(List<String> schedule){
        schedule.forEach(System.out::println);
    }

}
