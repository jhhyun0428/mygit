from github import Github

# Authenticate with GitHub API using personal access token
g = Github("token")

# Get the VS Code repository object
repo = g.get_repo("microsoft/vscode")

# Get all the commits in the repository
commits = repo.get_commits()

# Iterate through the commits and print their SHA and commit message
for commit in commits:
    print(f"{commit.sha} - {commit.commit.message}")
    tree = commit.tree
    # Replace <file_extension> with the actual extension you want to extract, e.g. ".py"
    source_files = [f for f in tree if f.type == "blob"]
    for f in source_files:
        print(f)

