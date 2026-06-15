import os
import re

def minify_content(text):
    # Remove consecutive empty lines to save tokens
    text = re.sub(r'\n\s*\n', '\n\n', text)
    # Strip leading/trailing whitespaces from each line (optional, but saves tokens, though it might ruin python formatting)
    # Python relies on indentation, so we CANNOT strip leading spaces for Python, but we can for others or just rely on removing blank lines.
    return text.strip()

def export_codebase(dir_path, exclude_dirs, allowed_exts):
    output_lines = ["# Codebase Architecture\n"]
    file_entries = []
    
    for root, dirs, files in os.walk(dir_path):
        dirs[:] = [d for d in dirs if not d.startswith('.') and d not in exclude_dirs]
        for f in files:
            if any(f.endswith(ext) for ext in allowed_exts):
                file_entries.append(os.path.join(root, f))
                
    # 1. Output minimal file list instead of ASCII tree (saves indentation tokens)
    output_lines.append("## File Structure")
    for f in file_entries:
        rel_path = os.path.relpath(f, dir_path).replace("\\", "/")
        output_lines.append(f"- {rel_path}")
        
    output_lines.append("\n## File Contents")
    
    # 2. Output file contents with markdown code blocks
    for f in file_entries:
        rel_path = os.path.relpath(f, dir_path).replace("\\", "/")
        ext = rel_path.split('.')[-1] if '.' in rel_path else ""
        
        # Map extension to markdown language
        lang_map = {"kt": "kotlin", "kts": "kotlin", "py": "python", "sq": "sql"}
        lang = lang_map.get(ext, ext)
        
        try:
            with open(f, 'r', encoding='utf-8') as file:
                content = minify_content(file.read())
                output_lines.append(f"\n### {rel_path}\n```{lang}\n{content}\n```")
        except Exception:
            pass

    return "\n".join(output_lines)

if __name__ == "__main__":
    root_dir = os.path.dirname(os.path.abspath(__file__))
    output_file = os.path.join(root_dir, "ai_codebase_context.md")
    
    exclude_dirs = {"build", ".gradle", ".idea", "venv", "__pycache__", "node_modules", "iosApp", "composeResources", "gradle"}
    allowed_exts = {".kt", ".kts", ".py", ".xml", ".sq", ".toml"}
    
    with open(output_file, 'w', encoding='utf-8') as f:
        f.write(export_codebase(root_dir, exclude_dirs, allowed_exts))
        
    print(f"✅ Token-optimized markdown exported to: {output_file}")
