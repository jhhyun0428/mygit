from github import Github
from extractIssueLinks import print_issue_links
from extractCommitIssueLinks import print_commit_links 
import sys
import time

# Authenticate with GitHub API using personal access token
g = Github("token")

# Get the VS Code repository object
repo = g.get_repo("elastic/elasticsearch")

# Get all the issues in the repository
issues = repo.get_issues(state="all") #open, all
# print("1:", issues.totalCount)
# print("2:", len(list(issues)))
# exit()
commits = repo.get_commits()
############### WRITE DATA #######################################################
import csv
original_stdout = sys.stdout

# Iterate through the issues and print their titles
i = 0
with open("data.csv", "w", newline="", encoding='utf-8') as fileX:
    sys.stdout = fileX # Change the standard output to the file we created.
    #for issue in issues:
        #try:
        #    if (i % 10 == 0):
        #        sys.stdout = original_stdout
        #        print("progress by " + str(i))
        #        sys.stdout = fileX
        #        i = i+1
        #    else:
        #        i = i+1
        #    print_issue_links(issue.number)
        #except Exception:
        #    sys.stdout = original_stdout
        #    print("exception happens!")
        #    time.sleep(60*60)
        #    sys.stdout = fileX
    for commit in commits:
        try:
            if (i % 10 == 0):
                sys.stdout = original_stdout
                print("progress by " + str(i))
                sys.stdout = fileX
                i = i+1
            else:
                i = i+1       
            print_commit_links(commit, repo)
        except Exception:
            sys.stdout = original_stdout
            print("exception happens!")
            time.sleep(60*60)
            sys.stdout = fileX

sys.stdout = original_stdout
print("End")
