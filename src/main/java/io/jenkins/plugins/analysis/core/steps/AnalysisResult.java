package io.jenkins.plugins.analysis.core.steps;

import hudson.model.Run;
import hudson.plugins.analysis.core.BuildResult;
import hudson.plugins.analysis.core.HistoryProvider;
import hudson.plugins.analysis.core.ParserResult;
import hudson.plugins.analysis.core.ReferenceProvider;
import hudson.plugins.analysis.core.ResultAction;

/**
 * FIXME: write comment.
 *
 * @author Ullrich Hafner
 */
public class AnalysisResult extends BuildResult {
    private final String id;
    private final String displayName;

    /**
     * Creates a new instance of {@link AnalysisResult}.
     *
     * @param build
     *            the current build as owner of this action
     * @param referenceProvider
     *            the build history
     * @param issues
     *            the parsed result with all annotations
     * @param defaultEncoding
     *            the default encoding to be used when reading and parsing files
     * @param id
     *            the parser group this result belongs to
     */
    public AnalysisResult(final Run build, final String defaultEncoding, final ParserResult issues,
            final ReferenceProvider referenceProvider, final HistoryProvider buildHistory, final String id) {
        super(build, referenceProvider, buildHistory, issues, defaultEncoding);

        this.id = id;
        displayName = issues.getDisplayName();

        serializeAnnotations(issues.getAnnotations());
    }

    @Override
    protected String getSerializationFileName() {
        return id + "-issues.xml";
    }

    @Override
    protected Class<? extends ResultAction<? extends BuildResult>> getResultActionType() {
        return null;
    }

    @Override
    public String getSummary() {
        return getIssueParser().getSummary(getNumberOfAnnotations(), getNumberOfModules());
    }

    private IssueParser getIssueParser() {
        return IssueParser.find(id);
    }

    @Override
    public String getDisplayName() {
        return getIssueParser().getLinkName();
    }


}